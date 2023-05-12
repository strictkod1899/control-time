package ru.strict.controltime.task.adapter.repository.task;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskJsonRepositoryError {
    public final String taskNotFoundErrorCode = "b71ff92b-001";

    public CodeableException errTaskNotFoundById(TaskId taskId) {
        var errMsg = String.format("task by id = '%s' not found", taskId);
        return new CodeableException(taskNotFoundErrorCode, errMsg);
    }
}
