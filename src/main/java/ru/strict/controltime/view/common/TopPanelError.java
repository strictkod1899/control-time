package ru.strict.controltime.view.common;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TopPanelError {
    public static String parentWindowForTopPanelIsRequiredErrorCode = "57e5d609-001";

    public CodeableException errParentWindowForTopPanelIsRequired() {
        return new CodeableException(parentWindowForTopPanelIsRequiredErrorCode, "parent window for TopPanel is required");
    }
}
