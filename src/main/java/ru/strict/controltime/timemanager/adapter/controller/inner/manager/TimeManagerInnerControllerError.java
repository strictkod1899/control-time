package ru.strict.controltime.timemanager.adapter.controller.inner.manager;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerInnerControllerError {
    public final String timeManagerUseCaseIsRequiredErrorCode = "SYS";

    public CodeableException errTimeManagerUseCaseIsRequired() {
        return new CodeableException(timeManagerUseCaseIsRequiredErrorCode, "timeManagerUseCase is required");
    }
}
