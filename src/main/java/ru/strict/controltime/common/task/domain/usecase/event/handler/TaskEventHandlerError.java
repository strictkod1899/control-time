package ru.strict.controltime.common.task.domain.usecase.event.handler;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskEventHandlerError {
    public final String handlersNotFoundErrorCode = "16c5f29a-001";

    public CodeableException errHandlersNotFound(TaskEventAction action) {
        var errMsg = String.format("TaskEvent handlers not found for action = '%s'", action);
        return new CodeableException(handlersNotFoundErrorCode, errMsg);
    }
}
