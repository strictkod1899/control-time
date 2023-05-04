package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.Errors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerUseCaseBuilder {
    TimeManagerRepository timeManagerRepository;
    TaskRepository taskRepository;
    NotificationPresenter notificationPresenter;
    TimeManagerPresenter timeManagerPresenter;

    Errors errors;

    public TimeManagerUseCaseBuilder() {
        errors = new Errors();
    }

    public TimeManagerUseCaseBuilder timeManagerRepository(TimeManagerRepository timeManagerRepository) {
        this.timeManagerRepository = timeManagerRepository;
        return this;
    }

    public TimeManagerUseCaseBuilder taskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public TimeManagerUseCaseBuilder notificationPresenter(NotificationPresenter notificationPresenter) {
        this.notificationPresenter = notificationPresenter;
        return this;
    }

    public TimeManagerUseCaseBuilder timeManagerPresenter(TimeManagerPresenter timeManagerPresenter) {
        this.timeManagerPresenter = timeManagerPresenter;
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
        if (timeManagerRepository == null) {
            errors.addError(TimeManagerUseCaseError.errTimeManagerRepositoryIsRequired());
        }
        if (taskRepository == null) {
            errors.addError(TimeManagerUseCaseError.errTaskRepositoryIsRequired());
        }
        if (notificationPresenter == null) {
            errors.addError(TimeManagerUseCaseError.errNotificationPresenterIsRequired());
        }
        if (timeManagerPresenter == null) {
            errors.addError(TimeManagerUseCaseError.errTimeManagerPresenterIsRequired());
        }
    }

    private TimeManagerUseCaseImpl createFromBuilder() {
        var timeManagerUseCase = new TimeManagerUseCaseImpl();
        timeManagerUseCase.timeManagerRepository = this.timeManagerRepository;
        timeManagerUseCase.taskRepository = this.taskRepository;
        timeManagerUseCase.notificationPresenter = this.notificationPresenter;
        timeManagerUseCase.timeManagerPresenter = this.timeManagerPresenter;

        return timeManagerUseCase;
    }
}
