package ru.strict.controltime.domain.entity.task;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskError {
    public final String messageIsEmptyErrorCode = "9a0bc086-001";
    public final String messageIsTooLongErrorCode = "9a0bc086-002";
    public final String taskIdIsRequiredErrorCode = "9a0bc086-003";
    public final String messageIsRequiredErrorCode = "9a0bc086-004";
    public final String sleepDurationIsRequiredErrorCode = "9a0bc086-005";
    public final String durationIsRequiredErrorCode = "9a0bc086-006";
    public final String sleepDurationIsTooSmallErrorCode = "9a0bc086-007";

    public CodeableException errMessageIsEmpty() {
        return new CodeableException(messageIsEmptyErrorCode, "Message is empty");
    }

    public CodeableException errMessageIsTooLong() {
        return new CodeableException(messageIsTooLongErrorCode, "Message is too long");
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

    public CodeableException errDurationIsRequired() {
        return new CodeableException(durationIsRequiredErrorCode, "Duration is required");
    }

    public CodeableException errSleepDurationIsTooSmall() {
        return new CodeableException(sleepDurationIsTooSmallErrorCode, "SleepDuration is too small");
    }
}
