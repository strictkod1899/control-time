package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.adapter.controller.inner.task.TaskInnerController;
import ru.strict.controltime.task.adapter.controller.inner.task.request.CreateTaskRequest;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.view.settings.component.tasks.SettingsListener;
import ru.strict.controltime.view.settings.gateway.TaskGateway;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsViewController implements SettingsListener {

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
    public void taskCreated(String message, long sleepDurationNanos) {
        taskGateway.createTask(message, sleepDurationNanos);
    }
}
