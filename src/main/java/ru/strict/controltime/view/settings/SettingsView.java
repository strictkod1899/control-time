package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.view.boundary.BaseView;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsView extends BaseView<SettingsViewState, SettingsViewModel> {



    public SettingsView(@Nonnull SettingsViewModel model) {
        super(model);
    }

    @Override
    protected void refresh() {
        switch (getModel().getState()) {
            case showTasks:
                showTasksSettings();
                break;
            case none:
                break;
            default:
                throw SettingsViewError.errUnsupportedViewState(getModel().getState());
        }
    }

    private void showTasksSettings() {

    }

    @Nonnull
    @Override
    protected Object getLockObject(@Nonnull SettingsViewState state) {
        return this;
    }
}
