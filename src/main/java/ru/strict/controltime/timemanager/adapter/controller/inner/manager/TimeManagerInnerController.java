package ru.strict.controltime.timemanager.adapter.controller.inner.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.validate.CommonValidator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerInnerController {
    TimeManagerUseCase timeManagerUseCase;

    public TimeManagerInnerController(TimeManagerUseCase timeManagerUseCase) {
        CommonValidator.throwIfNull(timeManagerUseCase, "timeManagerUseCase");

        this.timeManagerUseCase = timeManagerUseCase;
    }

    public void initTimeManager() {
        timeManagerUseCase.initTimeManager();
    }

    public TimeManager getActiveTimeManager() {
        return timeManagerUseCase.getActiveTimeManager();
    }
}
