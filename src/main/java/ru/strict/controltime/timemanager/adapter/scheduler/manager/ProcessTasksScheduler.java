package ru.strict.controltime.timemanager.adapter.scheduler.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.validate.CommonValidator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProcessTasksScheduler {
    TimeManagerUseCase timeManagerUseCase;

    public ProcessTasksScheduler(TimeManagerUseCase timeManagerUseCase) {
        CommonValidator.throwIfNull(timeManagerUseCase, "timeManagerUseCase");

        this.timeManagerUseCase = timeManagerUseCase;
    }

    public void processTasks() {
        timeManagerUseCase.processReadyTasks();
    }
}
