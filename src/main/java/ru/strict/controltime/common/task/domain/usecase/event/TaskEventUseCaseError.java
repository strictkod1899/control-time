package ru.strict.controltime.common.task.domain.usecase.event;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TaskEventUseCaseError {
    public final String eventIsRequiredErrorCode = "9dc17677-001";

    public CodeableException errEventIsRequired() {
        return new CodeableException(eventIsRequiredErrorCode, "TaskEvent is required'");
    }
}
