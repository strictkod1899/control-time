package ru.strict.controltime.domain.usecase.manager;

import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.task.Task;

import java.util.List;

public class TimeManagerSingleton {

    private volatile static TimeManager timeManager;

    private TimeManagerSingleton() {}

    public static TimeManager init(List<Task> tasks) {
        if (timeManager != null) {
            throw TimeManagerUseCaseError.errTaskManagerAlreadyInitialized();
        }

        TimeManagerSingleton.timeManager = TimeManager.init(tasks);
        return timeManager;
    }

    public static boolean isInitialized() {
        return timeManager != null;
    }

    public static TimeManager getInstance() {
        if (timeManager == null) {
            throw TimeManagerUseCaseError.errTaskManagerIsNotInitialized();
        }

        return TimeManagerSingleton.timeManager;
    }
}
