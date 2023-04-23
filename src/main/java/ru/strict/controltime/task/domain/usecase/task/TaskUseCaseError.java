package ru.strict.controltime.task.domain.usecase.task;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskUseCaseError {
    public final String taskRepositoryIsRequiredErrorCode = "SYS";
    public final String taskEventPublisherIsRequiredErrorCode = "SYS";

    public final String createTaskDataIsRequiredErrorCode = "c0962dd2-001";

    public CodeableException errTaskRepositoryIsRequired() {
        return new CodeableException(taskRepositoryIsRequiredErrorCode, "taskRepository is required");
    }
    public CodeableException errTaskEventPublisherIsRequired() {
        return new CodeableException(taskEventPublisherIsRequiredErrorCode, "taskEventPublisher is required");
    }

    public CodeableException errCreateTaskDataIsRequired() {
        return new CodeableException(createTaskDataIsRequiredErrorCode, "CreateTaskData is required");
    }
}
