package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerViewController {

    TimeManagerView view;
    TimeManagerViewModel model;

    private TimeManagerViewController(
            TimeManagerView view,
            TimeManagerViewModel model) {
        this.view = view;
        this.model = model;
    }

    public void showTimeManager(TimeManager timeManager) {
        model.setActualTimeManager(timeManager);
        view.refresh(TimeManagerViewState.init);
    }

    public void refreshTimeManager(TimeManager timeManager) {
        model.setActualTimeManager(timeManager);
        view.refresh(TimeManagerViewState.refreshTimeManager);
    }

    public void refreshComputerWorkDuration(Duration computerWorkDuration) {
        model.setComputerWorkDuration(computerWorkDuration);
        view.refresh(TimeManagerViewState.refreshComputerWorkDuration);
    }

    public static TimeManagerViewController init(String appPath) {
        var model = new TimeManagerViewModel();
        var view = TimeManagerView.init(model, appPath);

        return new TimeManagerViewController(view, model);
    }
}
