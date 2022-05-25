package com.javamaster.services;

import com.javamaster.converters.AttachmentToAttachmentResponseDtoConverter;
import com.javamaster.converters.CommentToCommentResponseDtoConverter;
import com.javamaster.converters.HistoryToHistoryDtoConverter;
import com.javamaster.dto.AttachmentResponseDto;
import com.javamaster.dto.CommentResponseDto;
import com.javamaster.dto.HistoryResponseDto;
import com.javamaster.dto.TicketOverview;
import com.javamaster.entities.Attachment;
import com.javamaster.entities.Comment;
import com.javamaster.entities.History;
import com.javamaster.entities.Ticket;
import com.javamaster.services.adapters.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketOverviewService implements TicketOverviewServiceAdapter {
    private final TicketServiceAdapter ticketService;

    private final AttachmentServiceAdapter attachmentService;
    private final CommentServiceAdapter commentService;
    private final HistoryServiceAdapter historyService;

    private final AttachmentToAttachmentResponseDtoConverter attachmentConverter;
    private final CommentToCommentResponseDtoConverter commentConverter;
    private final HistoryToHistoryDtoConverter historyConverter;

    @Override
    public TicketOverview getTicketOverview(Long ticketId) {
        Ticket ticket = ticketService.getById(ticketId);

        List<History> historyList = historyService.getHistoryByTicketId(ticketId);
        List<Comment> commentList = commentService.getCommentsByTicketId(ticketId);
        List<Attachment> attachmentList = attachmentService.getAttachmentsByTicketId(ticketId);

        List<AttachmentResponseDto> preparedAttachments = attachmentList.stream()
                .map(attachmentConverter::convert)
                .collect(Collectors.toList());
        List<HistoryResponseDto> preparedHistory = historyList.stream()
                .map(historyConverter::convert)
                .sorted((history1, history2) -> history2.getDate().compareTo(history1.getDate()))
                .limit(5)
                .collect(Collectors.toList());
        List<CommentResponseDto> preparedComments = commentList.stream()
                .map(commentConverter::convert)
                .sorted((comment1, comment2) -> comment2.getDate().compareTo(comment1.getDate()))
                .limit(5)
                .collect(Collectors.toList());


        return TicketOverview.builder()
                .id(ticketId)
                .name(ticket.getName())
                .description(ticket.getDescription())
                .createdOn(ticket.getCreatedOn())
                .status(ticket.getState().getValue())
                .category(ticket.getCategory() == null ? null : ticket.getCategory().getCategory())
                .urgency(ticket.getUrgency() == null ? null : ticket.getUrgency().getUrgency())
                .desiredResolutionDate(ticket.getDesiredResolutionDate())
                .owner(ticket.getOwner().getName())
                .approver(ticket.getApprover() == null ? null : ticket.getApprover().getName())
                .assignee(ticket.getAssignee() == null ? null : ticket.getAssignee().getName())
                .attachments(preparedAttachments)
                .history(preparedHistory)
                .comments(preparedComments)
                .build();
    }
}
