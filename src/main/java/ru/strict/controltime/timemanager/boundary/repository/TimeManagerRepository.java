package ru.strict.controltime.timemanager.boundary.repository;

import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

import java.util.Optional;

public interface TimeManagerRepository {
    void setActiveManager(TimeManager timeManager);
    Optional<TimeManager> getActiveManager();
}
