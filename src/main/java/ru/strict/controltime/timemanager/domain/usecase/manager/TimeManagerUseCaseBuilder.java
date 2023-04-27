package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.Errors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerUseCaseBuilder {
    NotificationPresenter notificationPresenter;
    TimeManagerRepository timeManagerRepository;
    TaskRepository taskRepository;

    Errors errors;

    public TimeManagerUseCaseBuilder() {
        errors = new Errors();
    }

    public TimeManagerUseCaseBuilder notificationPresenter(NotificationPresenter notificationPresenter) {
        this.notificationPresenter = notificationPresenter;
        return this;
    }

    public TimeManagerUseCaseBuilder timeManagerRepository(TimeManagerRepository timeManagerRepository) {
        this.timeManagerRepository = timeManagerRepository;
        return this;
    }

    public TimeManagerUseCaseBuilder taskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public TimeManagerUseCaseImpl build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (notificationPresenter == null) {
            errors.addError(TimeManagerUseCaseError.errNotificationPresenterIsRequired());
        }
        if (timeManagerRepository == null) {
            errors.addError(TimeManagerUseCaseError.errTimeManagerRepositoryIsRequired());
        }
        if (taskRepository == null) {
            errors.addError(TimeManagerUseCaseError.errTaskRepositoryIsRequired());
        }
    }

    private TimeManagerUseCaseImpl createFromBuilder() {
        var timeManagerUseCase = new TimeManagerUseCaseImpl();
        timeManagerUseCase.notificationPresenter = this.notificationPresenter;
        timeManagerUseCase.timeManagerRepository = this.timeManagerRepository;
        timeManagerUseCase.taskRepository = this.taskRepository;

        return timeManagerUseCase;
    }
}
