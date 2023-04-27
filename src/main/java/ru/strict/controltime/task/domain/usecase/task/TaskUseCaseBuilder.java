package ru.strict.controltime.task.domain.usecase.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.exception.Errors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskUseCaseBuilder {
    TaskRepository taskRepository;
    TaskEventPublisher taskEventPublisher;

    Errors errors;

    public TaskUseCaseBuilder() {
        errors = new Errors();
    }

    public TaskUseCaseBuilder taskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        return this;
    }

    public TaskUseCaseBuilder taskEventPublisher(TaskEventPublisher taskEventPublisher) {
        this.taskEventPublisher = taskEventPublisher;
        return this;
    }

    public TaskUseCaseImpl build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (taskRepository == null) {
            errors.addError(TaskUseCaseError.errTaskRepositoryIsRequired());
        }
        if (taskEventPublisher == null) {
            errors.addError(TaskUseCaseError.errTaskEventPublisherIsRequired());
        }
    }

    private TaskUseCaseImpl createFromBuilder() {
        var taskUseCase = new TaskUseCaseImpl();
        taskUseCase.taskRepository = this.taskRepository;
        taskUseCase.taskEventPublisher = this.taskEventPublisher;

        return taskUseCase;
    }
}
