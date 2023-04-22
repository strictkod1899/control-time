package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.exception.Errors;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskBuilder {
    TaskId id;
    Message message;
    Duration sleepDuration;

    Errors errors;

    public TaskBuilder() {
        this.errors = new Errors();
    }

    public TaskBuilder id(TaskId id) {
        this.id = id;
        return this;
    }

    public TaskBuilder message(Message message) {
        this.message = message;
        return this;
    }

    public TaskBuilder sleepDuration(Duration sleepDuration) {
        this.sleepDuration = sleepDuration;
        return this;
    }

    public Task build() {
        this.checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        return createFromBuilder();
    }

    private void checkRequiredFields() {
        if (this.id == null) {
            this.errors.addError(TaskError.errTaskIdIsRequired());
        }

        if (this.message == null) {
            this.errors.addError(TaskError.errMessageIsRequired());
        }

        if (this.sleepDuration == null) {
            this.errors.addError(TaskError.errSleepDurationIsRequired());
        }
    }

    private Task createFromBuilder() {
        var task = new Task();
        task.id = this.id;
        task.message = this.message;
        task.sleepDuration = this.sleepDuration;

        return task;
    }
}
