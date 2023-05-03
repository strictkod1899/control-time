package ru.strict.controltime.common.task.domain.usecase.event.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEventHandlerProvider {
    HashMap<TaskEventAction, List<TaskEventHandler>> actionHandlers;
    HashSet<TaskEventAction> skippedActions;

    public static TaskEventHandlerProvider init() {
        var provider = new TaskEventHandlerProvider();
        provider.actionHandlers = new HashMap<>();
        provider.skippedActions = new HashSet<>();

        return provider;
    }

    public List<TaskEventHandler> getHandlersForAction(TaskEventAction action) {
        if (isSkippedAction(action)) {
            return List.of();
        }

        if (!actionHandlers.containsKey(action)) {
            throw TaskEventHandlerError.errHandlersNotFound(action);
        }
        return actionHandlers.get(action);
    }

    public void putHandlers(TaskEventAction action, TaskEventHandler...handlers) {
        actionHandlers.put(action, List.of(handlers));
    }

    public void putSkippedAction(TaskEventAction action) {
        skippedActions.add(action);
    }

    private boolean isSkippedAction(TaskEventAction action) {
        return skippedActions.contains(action);
    }
}
