package com.javamaster.service;

import com.javamaster.entity.History;
import com.javamaster.entity.Mail;
import com.javamaster.entity.Ticket;
import com.javamaster.entity.User;
import com.javamaster.entity.enums.Action;
import com.javamaster.entity.enums.State;
import com.javamaster.entity.enums.UserRole;
import com.javamaster.service.adapters.ActionServiceAdapter;
import com.javamaster.service.adapters.EmailSenderServiceAdapter;
import com.javamaster.service.adapters.HistoryServiceAdapter;
import com.javamaster.service.adapters.UserServiceAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ActionService implements ActionServiceAdapter {
    private final UserServiceAdapter userService;
    private final HistoryServiceAdapter historyService;
    private final EmailSenderServiceAdapter emailSenderService;

    @Override
    public Map<String, Object> performAction(User user, Ticket ticket, String action) {
        State state = ticket.getState();

        String actionForHistoryRecord = "Ticket Status is changed";
        String descriptionForHistoryRecord;

        List<User> recipients;
        Mail mail;
        Map<String, Object> props = new HashMap<>();
        props.put("ticketId", ticket.getId());

        Map<String, Object> afterPerformAction = new HashMap<>();

        switch (action) {
            case "Save as Draft":
                if (ticket.getState() == null) {
                    actionForHistoryRecord = "Ticket is created";
                    descriptionForHistoryRecord = "Ticket is created";
                    ticket.setState(State.DRAFT);
                } else {
                    actionForHistoryRecord = "Ticket is edited";
                    descriptionForHistoryRecord = "Ticket is edited";
                }
                break;

            case "Submit":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.SUBMIT);
                if (ticket.getState() == null) {
                    actionForHistoryRecord = "Ticket is created";
                    descriptionForHistoryRecord = "Ticket is created";
                } else {
                    descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to NEW.";
                }

                ticket.setState(State.NEW);

                recipients = userService.findAllManagers();

                mail = prepareMail("New ticket for approval",
                        recipients,
                        new Mail.HtmlTemplate("template01NewTicketForManagers", props));

                afterPerformAction.put("mail", mail);

                break;


            case "Approve":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.APPROVE);
                ticket.setApprover(user);
                ticket.setState(State.APPROVED);
                descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to APPROVED.";

                recipients = userService.findAllEngineers();
                recipients.add(ticket.getOwner());
                recipients.add(ticket.getApprover());

                mail = prepareMail("Ticket was approved",
                        recipients,
                        new Mail.HtmlTemplate("template02ApprovedTicketForUsers", props));

                afterPerformAction.put("mail", mail);

                break;


            case "Decline":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.DECLINE);
                ticket.setApprover(user);
                ticket.setState(State.DECLINED);
                descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to DECLINED.";

                recipients = List.of(ticket.getOwner(), ticket.getApprover());

                props.put("ownerName", ticket.getOwner().getName());

                mail = prepareMail("Ticket was declined",
                        recipients,
                        new Mail.HtmlTemplate("template03DeclinedTicketForUser", props));

                afterPerformAction.put("mail", mail);

                break;

            case "Cancel":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.CANCEL);
                if (!user.getId().equals(ticket.getOwner().getId()) && state.equals(State.NEW)) {
                    ticket.setApprover(user);

                    recipients = List.of(ticket.getOwner(), ticket.getApprover());
                    mail = prepareMail("Ticket was cancelled",
                            recipients,
                            new Mail.HtmlTemplate("template04CancelledByManager", props));

                    afterPerformAction.put("mail", mail);

                } else {
                    ticket.setAssignee(user);

                    recipients = List.of(ticket.getOwner(), ticket.getApprover(), ticket.getAssignee());
                    mail = prepareMail("Ticket was cancelled",
                            recipients,
                            new Mail.HtmlTemplate("template05CancelledByEngineer", props));

                    afterPerformAction.put("mail", mail);

                }
                ticket.setState(State.CANCELED);
                descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to CANCELED.";
                break;

            case "Assign to Me":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.ASSIGN);
                ticket.setAssignee(user);
                ticket.setState(State.PROGRESS);
                descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to IN PROGRESS.";
                break;

            case "Done":
                checkUserPermissionToPerformAction(state, user.getRole(), ticket.getOwner().getRole(), Action.DONE);
                ticket.setState(State.DONE);
                descriptionForHistoryRecord = "Ticket Status is changed from " + state + " to DONE.";

                recipients = List.of(ticket.getOwner());
                props.put("ownerName", ticket.getOwner().getName());

                mail = prepareMail("Ticket was done",
                        recipients,
                        new Mail.HtmlTemplate("template06DoneTicketForOwner", props));

                afterPerformAction.put("mail", mail);

                break;

            case "Leave feedback":
                actionForHistoryRecord = "Feedback was provided";
                descriptionForHistoryRecord = "Feedback was provided";

                recipients = List.of(ticket.getAssignee());
                props.put("engineerName", ticket.getAssignee().getName());

                mail = prepareMail("Feedback was provided",
                        recipients,
                        new Mail.HtmlTemplate("template07ProvidedFeedbackForEngineer", props));

                afterPerformAction.put("mail", mail);
                break;

            default:
                throw new NotFoundException("Action " + action + "can't be performed");
        }

        afterPerformAction.put("history", prepareHistory(user, actionForHistoryRecord, descriptionForHistoryRecord));

        return afterPerformAction;
    }

    @Override
    public void completeAction(Ticket ticket, Map<String, Object> props) {
        History history = (History) props.get("history");
        if (history != null) {
            history.setTicket(ticket);
            historyService.saveHistory(history);
        }

        Mail mail = (Mail) props.get("mail");
        if (mail != null) {
            mail.getHtmlTemplate().getProps().put("ticketId", ticket.getId());
            emailSenderService.sendEmail(mail);
        }
    }

    @Override
    public List<Action> setPossibleAction(State state, UserRole userRole, UserRole owner) {
        if (state == null) {
            return List.of(Action.SUBMIT);
        }

        boolean ownerState = state.equals(State.DRAFT) || state.equals(State.DECLINED);
        if (userRole.equals(UserRole.EMPLOYEE)) {
            if (ownerState) {
                return List.of(Action.SUBMIT, Action.CANCEL);
            }
        }

        if (userRole.equals(UserRole.MANAGER)) {
            if (userRole.equals(owner) && ownerState) {
                return List.of(Action.SUBMIT, Action.CANCEL);
            }
            if (state.equals(State.NEW)) {
                return List.of(Action.APPROVE, Action.DECLINE, Action.CANCEL);
            }
        }

        if (userRole.equals(UserRole.ENGINEER)) {
            if (state.equals(State.APPROVED)) {
                return List.of(Action.ASSIGN, Action.CANCEL);
            }
            if (state.equals(State.PROGRESS)) {
                return List.of(Action.DONE);
            }
        }

        return List.of();
    }

    private void checkUserPermissionToPerformAction(State state, UserRole userRole, UserRole owner, Action action) {
        List<Action> possibleActions = setPossibleAction(state, userRole, owner);
        if (!possibleActions.contains(action)) {
            throw new ForbiddenException("Forbidden to proceed.");
        }
    }

    private Mail prepareMail(String subject, List<User> to, Mail.HtmlTemplate htmlTemplate) {
        return Mail.builder()
                .subject(subject)
                .to(to)
                .htmlTemplate(htmlTemplate)
                .build();
    }

    private History prepareHistory(User owner, String action, String description) {
        return History.builder()
                .owner(owner)
                .action(action)
                .description(description)
                .build();
    }
}