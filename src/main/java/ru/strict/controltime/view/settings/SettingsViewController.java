package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.view.settings.component.tasks.CreateTaskListener;
import ru.strict.controltime.view.settings.gateway.TaskGateway;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsViewController implements CreateTaskListener {

    TaskGateway taskGateway;

    SettingsView view;
    SettingsViewModel model;

    public SettingsViewController(
            @Component("appPath") String appPath,
            TaskGateway taskGateway) {
        CommonValidator.throwIfNull(appPath, "appPath");
        CommonValidator.throwIfNull(taskGateway, "taskGateway");

        this.taskGateway = taskGateway;

        model = new SettingsViewModel();
        view = new SettingsView(model, appPath, this);
    }

    public void showTasksSettings(List<Task> tasks) {
        model.setActualTasks(tasks);
        view.refresh(SettingsViewState.showTasks);
    }

    @Override
    public void createTask(String message, long sleepDurationNanos) {
        taskGateway.createTask(message, sleepDurationNanos);
    }
}
