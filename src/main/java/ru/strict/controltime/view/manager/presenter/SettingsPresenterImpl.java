package ru.strict.controltime.view.manager.presenter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.settings.SettingsViewController;
import ru.strict.controltime.view.settings.gateway.TaskGateway;
import ru.strict.validate.CommonValidator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsPresenterImpl implements SettingsPresenter {

    TaskGateway taskGateway;
    SettingsViewController settingsViewController;

    public SettingsPresenterImpl(
            TaskGateway taskGateway,
            SettingsViewController settingsViewController) {
        CommonValidator.throwIfNull(taskGateway, "taskGateway");
        CommonValidator.throwIfNull(settingsViewController, "settingsViewController");

        this.taskGateway = taskGateway;
        this.settingsViewController = settingsViewController;
    }

    @Override
    public void showTasksSettings() {
        var actualTasks = taskGateway.getActualTasks();
        settingsViewController.showTasksSettings(actualTasks);
    }
}
