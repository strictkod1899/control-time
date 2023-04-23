package ru.strict.controltime.task.domain.usecase.task;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskUseCaseError {
    public final String taskRepositoryIsRequiredErrorCode = "SYS";
    public final String taskEventPublisherIsRequiredErrorCode = "SYS";

    public final String createTaskDataIsRequiredErrorCode = "c0962dd2-001";
    public final String taskIdIsRequiredErrorCode = "c0962dd2-002";
    public final String taskNotFoundErrorCode = "c0962dd2-003";

    public CodeableException errTaskRepositoryIsRequired() {
        return new CodeableException(taskRepositoryIsRequiredErrorCode, "taskRepository is required");
    }

    public CodeableException errTaskEventPublisherIsRequired() {
        return new CodeableException(taskEventPublisherIsRequiredErrorCode, "taskEventPublisher is required");
    }

    public CodeableException errCreateTaskDataIsRequired() {
        return new CodeableException(createTaskDataIsRequiredErrorCode, "createTaskData is required");
    }

    public CodeableException errTaskIdIsRequired() {
        return new CodeableException(taskIdIsRequiredErrorCode, "taskId is required");
    }

    public CodeableException errTaskNotFoundById(TaskId taskId) {
        var errMsg = String.format("Task by id = '%s' not found", taskId);
        return new CodeableException(taskNotFoundErrorCode, errMsg);
    }
}
