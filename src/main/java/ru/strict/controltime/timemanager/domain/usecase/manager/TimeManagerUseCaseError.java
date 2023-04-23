package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerUseCaseError {
    public final String timeManagerRepositoryIsRequiredErrorCode = "SYS";
    public final String notificationPresenterIsRequiredErrorCode = "SYS";

    public CodeableException errTimeManagerRepositoryIsRequired() {
        return new CodeableException(timeManagerRepositoryIsRequiredErrorCode, "timeManagerRepository is required");
    }

    public CodeableException errNotificationPresenterIsRequired() {
        return new CodeableException(notificationPresenterIsRequiredErrorCode, "notificationPresenter is required");
    }
}
