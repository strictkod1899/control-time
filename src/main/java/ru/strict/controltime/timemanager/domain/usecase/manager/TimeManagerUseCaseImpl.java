package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase {

    TimeManagerRepository timeManagerRepository;
    TaskRepository taskRepository;
    NotificationPresenter notificationPresenter;
    TimeManagerPresenter timeManagerPresenter;

    @Override
    public void initTimeManager() {
        var currentTimeManagerOptional = timeManagerRepository.getActiveManager();

        if (currentTimeManagerOptional.isPresent()) {
            return;
        }

        var tasks = taskRepository.getAllTasks();
        var newTimeManager = TimeManager.init(tasks);
        timeManagerRepository.setActiveManager(newTimeManager);
        timeManagerPresenter.showTimeManager(newTimeManager);
    }

    @Override
    public TimeManager getActiveTimeManager() {
        return timeManagerRepository.getActiveManager().
                orElseThrow(TimeManagerUseCaseError::errActiveTimeManagerNotFound);
    }

    @Override
    public void processReadyTasks() {
        var timeManagerOptional = timeManagerRepository.getActiveManager();
        var timeManager = timeManagerOptional.orElseThrow(TimeManagerUseCaseError::errActiveTimeManagerNotFound);

        timeManager.getReadyTaskIds().
                forEach(readyTaskId -> processReadyTask(timeManager, readyTaskId));

        timeManagerRepository.setActiveManager(timeManager);
        timeManagerPresenter.refreshTimeManager(timeManager);
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
