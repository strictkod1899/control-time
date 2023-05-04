package ru.strict.controltime.timemanager.domain.usecase.task.event.handler;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskHandlerError {
    public final String timeManagerRepositoryIsRequiredErrorCode = "SYS";
    public final String taskRepositoryIsRequiredErrorCode = "SYS";
    public final String timeManagerPresenterIsRequiredErrorCode = "SYS";

    public final String taskNotFoundErrorCode = "fa755e9e-001";
    public final String activeTimeManagerNotFoundErrorCode = "fa755e9e-002";

    public CodeableException errTimeManagerRepositoryIsRequired() {
        return new CodeableException(timeManagerRepositoryIsRequiredErrorCode, "timeManagerRepository is required");
    }

    public CodeableException errTaskRepositoryIsRequired() {
        return new CodeableException(taskRepositoryIsRequiredErrorCode, "taskRepository is required");
    }

    public CodeableException errTimeManagerPresenterIsRequired() {
        return new CodeableException(timeManagerPresenterIsRequiredErrorCode, "timeManagerPresenter is required");
    }

    public CodeableException errTaskNotFoundById(TaskId taskId) {
        var errMsg = String.format("task by id = '%s' not found", taskId);
        return new CodeableException(taskNotFoundErrorCode, errMsg);
    }

    public CodeableException errActiveTimeManagerNotFound() {
        return new CodeableException(activeTimeManagerNotFoundErrorCode, "active timeManager not found");
    }
}
