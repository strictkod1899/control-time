package ru.strict.controltime.view.settings;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class SettingsViewError {
    public final String unsupportedViewStateErrorCode = "5a1625a3-001";

    public CodeableException errUnsupportedViewState(SettingsViewState state) {
        var errMsg = String.format("unsupported SettingsViewState = '%s'", state);
        return new CodeableException(unsupportedViewStateErrorCode, errMsg);
    }
}
