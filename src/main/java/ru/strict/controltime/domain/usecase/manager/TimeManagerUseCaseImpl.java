package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.boundary.model.CreateTaskParams;
import ru.strict.controltime.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.boundary.repository.TaskRepository;
import ru.strict.controltime.boundary.usecase.TimeManagerProcessUseCase;
import ru.strict.controltime.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.SleepDuration;
import ru.strict.controltime.domain.entity.task.Task;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase, TimeManagerProcessUseCase {

    TaskRepository taskRepository;
    NotificationPresenter notificationPresenter;

    TimeManager timeManager;

    @Override
    public void addTask(CreateTaskParams createTaskParams) {
        if (createTaskParams == null) {
            throw TimeManagerUseCaseError.errCreateTaskParamIsRequired();
        }

        var taskMessage = Message.from(createTaskParams.getMessage());
        var sleepDuration = SleepDuration.from(createTaskParams.getSleepDuration());
        var task = Task.init(taskMessage, sleepDuration);

        taskRepository.insert(task);
        timeManager.addTask(task);
    }

    @Override
    public void deleteTask(String taskId) {
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
