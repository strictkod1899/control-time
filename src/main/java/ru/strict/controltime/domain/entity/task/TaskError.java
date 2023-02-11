package ru.strict.controltime.domain.entity.task;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskError {
    public final String messageIsEmptyErrorCode = "9a0bc086-001";
    public final String messageTooLongErrorCode = "9a0bc086-002";
    public final String taskIdIsRequiredErrorCode = "9a0bc086-003";
    public final String messageIsRequiredErrorCode = "9a0bc086-004";
    public final String sleepDurationIsRequiredErrorCode = "9a0bc086-005";
    public final String taskIsNotReadyErrorCode = "9a0bc086-006";
    public final String tasksListIsRequiredErrorCode = "9a0bc086-007";
    public final String doubledTaskErrorCode = "9a0bc086-008";
    public final String taskIsRequiredErrorCode = "9a0bc086-009";

    public CodeableException errMessageIsEmpty() {
        return new CodeableException(messageIsEmptyErrorCode, "Message is empty");
    }

    public CodeableException errMessageTooLong() {
        return new CodeableException(messageTooLongErrorCode, "Message is too long");
    }

    public CodeableException errTaskIdIsRequired() {
        return new CodeableException(taskIdIsRequiredErrorCode, "TaskID is required");
    }

    public CodeableException errMessageIsRequired() {
        return new CodeableException(messageIsRequiredErrorCode, "Message is required");
    }

    public CodeableException errSleepDurationIsRequired() {
        return new CodeableException(sleepDurationIsRequiredErrorCode, "SleepDuration is required");
    }

    public CodeableException errTaskIsNotReady() {
        return new CodeableException(taskIsNotReadyErrorCode, "Task is not ready");
    }

    public CodeableException errTasksListIsRequired() {
        return new CodeableException(tasksListIsRequiredErrorCode, "Tasks list is required");
    }

    public CodeableException errDoubledTaskById(TaskId taskId) {
        return new CodeableException(doubledTaskErrorCode, String.format("Doubled task by id = %s", taskId));
    }

    public CodeableException errTaskIsRequired() {
        return new CodeableException(taskIsRequiredErrorCode, "Task is required");
    }
}
