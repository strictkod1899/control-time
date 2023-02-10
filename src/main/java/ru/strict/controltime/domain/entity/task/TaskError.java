package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class TaskError {
    String messageIsEmptyErrorCode = "9a0bc086-001";
    String messageTooLongErrorCode = "9a0bc086-002";
    String taskIdIsRequiredErrorCode = "9a0bc086-003";
    String messageIsRequiredErrorCode = "9a0bc086-004";
    String sleepDurationIsRequiredErrorCode = "9a0bc086-005";
    String taskIsNotReadyErrorCode = "9a0bc086-006";

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
}
