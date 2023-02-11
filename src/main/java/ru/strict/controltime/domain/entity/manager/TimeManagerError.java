package ru.strict.controltime.domain.entity.manager;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerError {
    public final String taskNotFoundErrorCode = "35aebaec-001";
    public final String timeManagerIdIsRequiredErrorCode = "35aebaec-002";

    public CodeableException errTaskNotFound(TaskId taskId) {
        return new CodeableException(taskNotFoundErrorCode, String.format("Task not found by id = '%s'", taskId));
    }

    public CodeableException errTimeManagerId() {
        return new CodeableException(timeManagerIdIsRequiredErrorCode, "TimeManagerId is required");
    }
}
