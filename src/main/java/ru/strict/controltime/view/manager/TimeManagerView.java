package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.view.manager.component.TimeManagerWindow;
import ru.strict.controltime.view.manager.presenter.SettingsPresenter;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.BaseView;

import javax.annotation.Nonnull;
import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerView {

    final TimeManagerWindow window;

    TimeManager actualTimeManager;
    Duration computerWorkDuration;

    public TimeManagerView(String appPath, SettingsPresenter settingsPresenter) {
        this.window = new TimeManagerWindow(appPath, settingsPresenter);
    }

    public void show(TimeManager timeManager) {
        CommonValidator.throwIfNull(timeManager, "timeManager");

        this.actualTimeManager = timeManager;

        window.initTimeManager(actualTimeManager);
        window.show();
    }

    public void refreshActualTimeManager(TimeManager timeManager) {
        CommonValidator.throwIfNull(timeManager, "timeManager");

        this.actualTimeManager = timeManager;
        timeManager.getManageTasks().
                forEach(window::addOrRefreshTask);
    }

    public void refreshComputerWorkDuration(Duration computerWorkDuration) {
        CommonValidator.throwIfNull(computerWorkDuration, "computerWorkDuration");

        this.computerWorkDuration = computerWorkDuration;
        window.refreshComputerWorkDuration(computerWorkDuration);
    }
}
