package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingsViewController {

    SettingsView view;
    SettingsViewModel model;

    public SettingsViewController() {
        model = new SettingsViewModel();
        view = new SettingsView(model);
    }

    public void showTasksSettings(List<Task> tasks) {
        model.setExistsTasks(tasks);
        view.refresh(SettingsViewState.showTasks);
    }
}
