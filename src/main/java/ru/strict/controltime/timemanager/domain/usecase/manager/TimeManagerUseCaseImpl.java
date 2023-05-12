package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.validate.CommonValidator;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase {

    TimeManagerRepository timeManagerRepository;
    TaskRepository taskRepository;
    NotificationPresenter notificationPresenter;
    TimeManagerPresenter timeManagerPresenter;

    public TimeManagerUseCaseImpl(
            TimeManagerRepository timeManagerRepository,
            TaskRepository taskRepository,
            NotificationPresenter notificationPresenter,
            TimeManagerPresenter timeManagerPresenter) {
        CommonValidator.throwIfNull(timeManagerRepository, "timeManagerRepository");
        CommonValidator.throwIfNull(taskRepository, "taskRepository");
        CommonValidator.throwIfNull(notificationPresenter, "notificationPresenter");
        CommonValidator.throwIfNull(timeManagerPresenter, "timeManagerPresenter");

        this.timeManagerRepository = timeManagerRepository;
        this.taskRepository = taskRepository;
        this.notificationPresenter = notificationPresenter;
        this.timeManagerPresenter = timeManagerPresenter;
    }

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

    private void processReadyTask(TimeManager timeManager, TaskId readyTaskId) {
        var taskOptional = timeManager.getTask(readyTaskId);
        var task = taskOptional.orElseThrow(
                () -> TimeManagerUseCaseError.errReadyTaskNotFoundById(readyTaskId)
        );

        notificationPresenter.showNotification(task);
        timeManager.markTaskAsProcessed(readyTaskId);
    }
}
