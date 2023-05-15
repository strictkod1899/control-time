package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.view.manager.presenter.SettingsPresenter;
import ru.strict.controltime.view.settings.SettingsViewController;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerViewController {

    SettingsPresenter settingsPresenter;

    TimeManagerView view;
    TimeManagerViewModel model;

    public TimeManagerViewController(
            @Component("appPath") String appPath,
            SettingsPresenter settingsPresenter) {
        CommonValidator.throwIfNullOrEmpty(appPath, "appPath");
        CommonValidator.throwIfNull(settingsPresenter, "settingsPresenter");

        this.settingsPresenter = settingsPresenter;

        this.model = new TimeManagerViewModel();
        this.view = new TimeManagerView(model, appPath, settingsPresenter);
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
