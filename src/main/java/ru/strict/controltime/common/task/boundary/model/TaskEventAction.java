package ru.strict.controltime.common.task.boundary.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Getter
@RequiredArgsConstructor
public enum TaskEventAction {
    CREATED("taskCreated"),
    DELETED("taskDeleted");

    private static final HashMap<String, TaskEventAction> VALUES_BY_ACTION = new HashMap<>();

    static {
        for (var item : values()) {
            VALUES_BY_ACTION.put(item.toString(), item);
        }
    }

    private final String action;

    @Override
    public String toString() {
        return action;
    }

    public static TaskEventAction of(String action) {
        return VALUES_BY_ACTION.get(action);
    }
}
