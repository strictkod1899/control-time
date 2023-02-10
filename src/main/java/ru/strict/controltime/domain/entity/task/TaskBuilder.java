package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.exception.Errors;

import java.time.Duration;
import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskBuilder {
    TaskId id;
    Message message;
    Duration sleepDuration;
    Instant startedAt;
    Instant lastProcessedAt;

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

    public TaskBuilder startedAt(Instant startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public TaskBuilder lastProcessedAt(Instant lastProcessedAt) {
        this.lastProcessedAt = lastProcessedAt;
        return this;
    }

    /**
     * @throws ru.strict.exception.Errors.ErrorsException
     */
    public Task build() {
        this.checkRequiredFields();
        this.fillDefaultFields();

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

        if (errors.isPresent()) {
            throw errors.toException();
        }
    }


    private void fillDefaultFields() {
        if (this.startedAt == null) {
            this.startedAt = Instant.now();
        }
    }

    private Task createFromBuilder() {
        var task = new Task();
        task.id = this.id;
        task.message = this.message;
        task.sleepDuration = this.sleepDuration;
        task.startedAt = this.startedAt;
        task.lastProcessedAt = this.lastProcessedAt;

        return task;
    }
}
