package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerViewController {

    TimeManagerView view;
    TimeManagerViewModel model;

    public TimeManagerViewController(@Component("appPath") String appPath) {
        CommonValidator.throwIfNullOrEmpty(appPath, "appPath");

        var model = new TimeManagerViewModel();
        var view = new TimeManagerView(model, appPath);

        this.model = model;
        this.view = view;
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
}
