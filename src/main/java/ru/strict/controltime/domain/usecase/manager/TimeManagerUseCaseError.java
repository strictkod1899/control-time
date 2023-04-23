package ru.strict.controltime.domain.usecase.manager;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerUseCaseError {
    public final String taskRepositoryIsRequiredErrorCode = "SYS";
    public final String notificationPresenterIsRequiredErrorCode = "SYS";

    public final String createTaskParamIsRequiredErrorCode = "c0962dd2-001";
    public final String taskManagerAlreadyInitializedErrorCode = "c0962dd2-002";
    public final String taskManagerIsNotInitializedErrorCode = "c0962dd2-003";

    public CodeableException errTaskRepositoryIsRequired() {
        return new CodeableException(taskRepositoryIsRequiredErrorCode, "taskRepository is required");
    }

    public CodeableException errNotificationPresenterIsRequired() {
        return new CodeableException(notificationPresenterIsRequiredErrorCode, "notificationPresenter is required");
    }

    public CodeableException errCreateTaskParamIsRequired() {
        return new CodeableException(createTaskParamIsRequiredErrorCode, "CreateTaskParam is required");
    }

    public CodeableException errTaskManagerAlreadyInitialized() {
        return new CodeableException(taskManagerAlreadyInitializedErrorCode, "TaskManager already initialized");
    }

    public CodeableException errTaskManagerIsNotInitialized() {
        return new CodeableException(taskManagerIsNotInitializedErrorCode, "TaskManager is not initialized");
    }
}
