package ru.strict.controltime.initservice;

import ru.strict.controltime.timemanager.adapter.controller.inner.manager.TimeManagerInnerController;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.ioc.annotation.Component;

public class InnerControllerInit {

    @Component
    public TimeManagerInnerController timeManagerInnerController(
            TimeManagerUseCase timeManagerUseCase) {
        return TimeManagerInnerController.from(timeManagerUseCase);
    }
}
