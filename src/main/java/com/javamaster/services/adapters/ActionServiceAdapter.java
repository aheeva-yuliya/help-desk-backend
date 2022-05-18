package com.javamaster.services.adapters;

import com.javamaster.entities.Ticket;
import com.javamaster.entities.User;
import com.javamaster.entities.enums.Action;
import com.javamaster.entities.enums.State;

import java.util.List;
import java.util.Map;

public interface ActionServiceAdapter {
    Map<String, Object> performAction(User user, Ticket ticket, String action);

    void completeAction(Ticket ticket, Map<String, Object> props);

    List<Action> setPossibleAction(State state, User user, User owner);
}
