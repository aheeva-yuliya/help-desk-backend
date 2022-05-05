package com.javamaster.service.adapters;

import com.javamaster.entity.Ticket;
import com.javamaster.entity.User;
import com.javamaster.entity.enums.Action;
import com.javamaster.entity.enums.State;
import com.javamaster.entity.enums.UserRole;

import java.util.List;
import java.util.Map;

public interface ActionServiceAdapter {
    Map<String, Object> performAction(User user, Ticket ticket, String action);

    void completeAction(Ticket ticket, Map<String, Object> props);

    List<Action> setPossibleAction(State state, User user, User owner);
}
