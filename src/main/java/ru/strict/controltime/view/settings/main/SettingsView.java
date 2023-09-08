package ru.strict.controltime.view.settings.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.view.settings.main.component.TasksSettingsWindow;
import ru.strict.controltime.view.settings.task.create.CreateTaskViewController;
import ru.strict.event.EventBroker;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsView {

    EventBroker viewEventBroker;
    CreateTaskViewController createTaskViewController;

    String appPath;
    List<Task> actualTasks;

    public SettingsView(
            @Component("appPath") String appPath,
            EventBroker viewEventBroker,
            CreateTaskViewController createTaskViewController
    ) {
        this.appPath = appPath;
        this.viewEventBroker = viewEventBroker;
        this.createTaskViewController = createTaskViewController;
        this.actualTasks = new ArrayList<>();
    }

    private void showTasksSettings() {
        CommonValidator.throwIfNull(model.getActualTasks(), "actualTasks");

        var window = new TasksSettingsWindow(appPath, getModel().getActualTasks(), createTaskListener);
        window.show();
    }
}
