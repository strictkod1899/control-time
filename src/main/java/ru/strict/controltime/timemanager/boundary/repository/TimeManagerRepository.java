package ru.strict.controltime.timemanager.boundary.repository;

import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

public interface TimeManagerRepository {
    void setActiveManager(TimeManager timeManager);
    TimeManager getActiveManager();
}
