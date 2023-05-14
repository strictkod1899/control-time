package ru.strict.controltime.timemanager.domain.entity.managetask;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.exception.CodeableException;

@UtilityClass
public class ManageTaskError {
    public final String taskIsRequiredErrorCode = "5657f13a-001";
    public final String taskIdIsRequiredErrorCode = "5657f13a-002";
    public final String taskIsNotReadyErrorCode = "5657f13a-003";
    public final String manageTaskListIsRequiredErrorCode = "5657f13a-004";
    public final String doubledTaskErrorCode = "5657f13a-005";
    public final String taskNotFoundErrorCode = "5657f13a-006";
    public final String taskStartedAtIsRequiredErrorCode = "5657f13a-007";
    public final String taskListIsRequiredErrorCode = "5657f13a-008";

    public CodeableException errTaskIsRequired() {
        return new CodeableException(taskIsRequiredErrorCode, "Task is required");
    }

    public CodeableException errTaskIdIsRequired() {
        return new CodeableException(taskIdIsRequiredErrorCode, "TaskID is required");
    }

    public CodeableException errTaskIsNotReady() {
        return new CodeableException(taskIsNotReadyErrorCode, "Task is not ready");
    }

    public CodeableException errManageTaskListIsRequired() {
        return new CodeableException(manageTaskListIsRequiredErrorCode, "ManageTask list is required");
    }

    public CodeableException errDoubledTaskById(TaskId taskId) {
        var errMsg = String.format("Doubled task by id = '%s'", taskId);
        return new CodeableException(doubledTaskErrorCode, errMsg);
    }

    public CodeableException errTaskNotFoundById(TaskId taskId) {
        return new CodeableException(taskNotFoundErrorCode, String.format("Task not found by id = '%s'", taskId));
    }

    public CodeableException errTaskStartedAtIsRequired() {
        return new CodeableException(taskStartedAtIsRequiredErrorCode, "Task startedAt is required");
    }

    public CodeableException errTaskListIsRequired() {
        return new CodeableException(taskListIsRequiredErrorCode, "Task list is required");
    }
}
