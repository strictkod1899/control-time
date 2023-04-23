package ru.strict.controltime.domain.usecase.manager;

import ru.strict.controltime.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.boundary.repository.TaskRepository;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.exception.Errors;

public class TimeManagerUseCaseBuilder {
    TaskRepository taskRepository;
    NotificationPresenter notificationPresenter;
    TimeManager timeManager;

    Errors errors;

    public TimeManagerUseCaseBuilder() {
        errors = new Errors();
    }

    public TimeManagerUseCaseBuilder taskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public TimeManagerUseCaseBuilder notificationPresenter(NotificationPresenter notificationPresenter) {
        this.notificationPresenter = notificationPresenter;
        return this;
    }

    public TimeManagerUseCaseImpl build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        initTimeManager();

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (taskRepository == null) {
            errors.addError(TimeManagerUseCaseError.errTaskRepositoryIsRequired());
        }

        if (notificationPresenter == null) {
            errors.addError(TimeManagerUseCaseError.errNotificationPresenterIsRequired());
        }
    }

    private void initTimeManager() {
        var tasks = taskRepository.getAllTasks();
        this.timeManager = TimeManager.init(tasks);
    }

    private TimeManagerUseCaseImpl createFromBuilder() {
        var timeManagerUseCase = new TimeManagerUseCaseImpl();
        timeManagerUseCase.taskRepository = this.taskRepository;
        timeManagerUseCase.notificationPresenter = this.notificationPresenter;
        timeManagerUseCase.timeManager = this.timeManager;

        return timeManagerUseCase;
    }
}
