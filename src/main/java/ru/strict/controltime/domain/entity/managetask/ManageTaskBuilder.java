package ru.strict.controltime.domain.entity.managetask;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.exception.Errors;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManageTaskBuilder {
    Task task;
    Instant startedAt;
    Instant lastProcessedAt;

    Errors errors;

    public ManageTaskBuilder() {
        this.errors = new Errors();
    }

    public ManageTaskBuilder task(Task task) {
        this.task = task;
        return this;
    }

    public ManageTaskBuilder startedAt(Instant startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public ManageTaskBuilder lastProcessedAt(Instant lastProcessedAt) {
        this.lastProcessedAt = lastProcessedAt;
        return this;
    }

    public ManageTask build() {
        this.checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (this.task == null) {
            this.errors.addError(ManageTaskError.errTaskIsRequired());
        }

        if (this.startedAt == null) {
            this.errors.addError(ManageTaskError.errTaskStartedAtIsRequired());
        }
    }

    private ManageTask createFromBuilder() {
        var manageTask = new ManageTask();
        manageTask.task = this.task;
        manageTask.startedAt = this.startedAt;
        manageTask.lastProcessedAt = this.lastProcessedAt;

        return manageTask;
    }
}
