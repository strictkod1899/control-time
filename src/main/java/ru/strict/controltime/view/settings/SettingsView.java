package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.settings.component.tasks.SettingsListener;
import ru.strict.controltime.view.settings.component.tasks.TasksSettingsWindow;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.BaseView;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsView extends BaseView<SettingsViewState, SettingsViewModel> {

    final String appPath;
    final SettingsListener settingsListener;

    public SettingsView(
            SettingsViewModel model,
            @Component("appPath") String appPath,
            SettingsListener settingsListener) {
        super(model);
        this.appPath = appPath;
        this.settingsListener = settingsListener;
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
        CommonValidator.throwIfNull(getModel().getActualTasks(), "actualTasks");

        var window = new TasksSettingsWindow(appPath, getModel().getActualTasks(), settingsListener);
        window.show();
    }

    @Nonnull
    @Override
    protected Object getLockObject(@Nonnull SettingsViewState state) {
        return this;
    }
}
