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
}
