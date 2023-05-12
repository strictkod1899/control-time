package ru.strict.controltime.view.notification;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class NotificationViewError {
    public final String unsupportedViewStateErrorCode = "a0f93361-001";

    public CodeableException errUnsupportedViewState(NotificationViewState state) {
        var errMsg = String.format("unsupported NotificationViewState = '%s'", state);
        return new CodeableException(unsupportedViewStateErrorCode, errMsg);
    }
}
