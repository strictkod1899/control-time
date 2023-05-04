package ru.strict.controltime.timemanager.domain.usecase.task.event.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.Errors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddTaskHandlerBuilder {
    TimeManagerRepository timeManagerRepository;
    TaskRepository taskRepository;
    TimeManagerPresenter timeManagerPresenter;

    Errors errors;

    public AddTaskHandlerBuilder() {
        errors = new Errors();
    }

    public AddTaskHandlerBuilder timeManagerRepository(TimeManagerRepository timeManagerRepository) {
        this.timeManagerRepository = timeManagerRepository;
        return this;
    }

    public AddTaskHandlerBuilder taskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public AddTaskHandlerBuilder timeManagerPresenter(TimeManagerPresenter timeManagerPresenter) {
        this.timeManagerPresenter = timeManagerPresenter;
        return this;
    }

    public AddTaskHandler build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (timeManagerRepository == null) {
            errors.addError(TaskHandlerError.errTimeManagerRepositoryIsRequired());
        }
        if (taskRepository == null) {
            errors.addError(TaskHandlerError.errTaskRepositoryIsRequired());
        }
        if (timeManagerPresenter == null) {
            errors.addError(TaskHandlerError.errTimeManagerPresenterIsRequired());
        }
    }

    private AddTaskHandler createFromBuilder() {
        var addTaskHandler = new AddTaskHandler();
        addTaskHandler.timeManagerRepository = this.timeManagerRepository;
        addTaskHandler.taskRepository = this.taskRepository;
        addTaskHandler.timeManagerPresenter = this.timeManagerPresenter;

        return addTaskHandler;
    }
}
