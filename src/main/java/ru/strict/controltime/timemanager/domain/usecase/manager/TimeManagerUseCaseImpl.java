package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.boundary.model.CreateTaskData;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.boundary.usecase.TaskUseCase;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.SleepDuration;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase {

    NotificationPresenter notificationPresenter;
    TimeManagerRepository timeManagerRepository;

    @Override
    public void initTimeManager() {
        throw new UnsupportedOperationException("impl me");
    }

    @Override
    public void processReadyTasks() {
        throw new UnsupportedOperationException("impl me");
    }

    public static TimeManagerUseCaseBuilder builder() {
        return new TimeManagerUseCaseBuilder();
    }
}
