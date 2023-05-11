package ru.strict.controltime.view.manager;

import lombok.experimental.UtilityClass;
import ru.strict.exception.CodeableException;

@UtilityClass
public class TimeManagerViewError {
    public final String unsupportedViewStateErrorCode = "de5989a8-001";

    public CodeableException errUnsupportedViewState(TimeManagerViewState state) {
        var errMsg = String.format("unsupported TimeManagerViewState = '%s'", state);
        return new CodeableException(unsupportedViewStateErrorCode, errMsg);
    }
}
