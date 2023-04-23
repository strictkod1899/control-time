package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.TaskId;
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
        var timeManagerOptional = timeManagerRepository.getActiveManager();
        var timeManager = timeManagerOptional.orElseThrow(TimeManagerUseCaseError::errActiveTimeManagerNotFound);

        timeManager.getReadyTaskIds().
                forEach(readyTaskId -> processReadyTask(timeManager, readyTaskId));

        timeManagerRepository.setActiveManager(timeManager);
    }

    public static TimeManagerUseCaseBuilder builder() {
        return new TimeManagerUseCaseBuilder();
    }

    private void processReadyTask(TimeManager timeManager, TaskId readyTaskId) {
        var messageOptional = timeManager.getTaskMessage(readyTaskId);
        var message = messageOptional.orElseThrow(
                () -> TimeManagerUseCaseError.errReadyTaskNotFoundById(readyTaskId)
        );

        notificationPresenter.showMessage(message);
        timeManager.markTaskAsProcessed(readyTaskId);
    }
}
