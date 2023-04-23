package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerUseCaseError {
    public final String timeManagerRepositoryIsRequiredErrorCode = "SYS";
    public final String notificationPresenterIsRequiredErrorCode = "SYS";

    public final String activeTimeManagerNotFoundErrorCode = "8e022e17-001";
    public final String readyTaskNotFoundErrorCode = "8e022e17-002";

    public CodeableException errTimeManagerRepositoryIsRequired() {
        return new CodeableException(timeManagerRepositoryIsRequiredErrorCode, "timeManagerRepository is required");
    }

    public CodeableException errNotificationPresenterIsRequired() {
        return new CodeableException(notificationPresenterIsRequiredErrorCode, "notificationPresenter is required");
    }

    public CodeableException errActiveTimeManagerNotFound() {
        return new CodeableException(activeTimeManagerNotFoundErrorCode, "active TimeManager not found");
    }

    public CodeableException errReadyTaskNotFoundById(TaskId taskId) {
        var errMsg = String.format("ready task by id = '%s' not found", taskId);
        return new CodeableException(readyTaskNotFoundErrorCode, errMsg);
    }
}
