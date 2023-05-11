package ru.strict.controltime.timemanager.boundary.usecase;

import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

public interface TimeManagerUseCase {
    void initTimeManager();
    TimeManager getActiveTimeManager();
    void processReadyTasks();
}
