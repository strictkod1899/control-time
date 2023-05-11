package ru.strict.controltime.view.manager.scheduler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.adapter.controller.inner.manager.TimeManagerInnerController;
import ru.strict.controltime.view.manager.TimeManagerViewController;
import ru.strict.validate.CommonValidator;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTimeManagerScheduler {

    TimeManagerViewController timeManagerViewController;
    TimeManagerInnerController timeManagerInnerController;

    public RefreshTimeManagerScheduler(
            TimeManagerViewController timeManagerViewController,
            TimeManagerInnerController timeManagerInnerController) {
        CommonValidator.throwIfNull(timeManagerViewController, "timeManagerViewController");
        CommonValidator.throwIfNull(timeManagerInnerController, "timeManagerInnerController");

        this.timeManagerViewController = timeManagerViewController;
        this.timeManagerInnerController = timeManagerInnerController;
    }

    public void refreshTimeManager() {
        var timeManager = timeManagerInnerController.getActiveTimeManager();
        timeManagerViewController.refreshTimeManager(timeManager);
    }
}
