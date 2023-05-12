package ru.strict.controltime.timemanager.adapter.repository.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerInMemoryRepository implements TimeManagerRepository {

    volatile TimeManager activeTimeManager;

    public TimeManagerInMemoryRepository(){}

    @Override
    public synchronized void setActiveManager(TimeManager timeManager) {
        this.activeTimeManager = timeManager;
    }

    @Override
    public Optional<TimeManager> getActiveManager() {
        return Optional.ofNullable(activeTimeManager);
    }
}
