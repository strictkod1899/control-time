package ru.strict.controltime.timemanager.adapter.controller.inner.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerInnerController {
    TimeManagerUseCase timeManagerUseCase;

    private TimeManagerInnerController() {}

    public static TimeManagerInnerController from(TimeManagerUseCase timeManagerUseCase) {
        if (timeManagerUseCase == null) {
            throw TimeManagerInnerControllerError.errTimeManagerUseCaseIsRequired();
        }

        var controller = new TimeManagerInnerController();
        controller.timeManagerUseCase = timeManagerUseCase;

        return controller;
    }

    public void initTimeManager() {
        timeManagerUseCase.initTimeManager();
    }

    public TimeManager getActiveTimeManager() {
        return timeManagerUseCase.getActiveTimeManager();
    }
}
