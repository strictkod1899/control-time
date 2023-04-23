package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.strict.exception.Errors;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PACKAGE)
public class Task {
    TaskId id;
    Message message;
    SleepDuration sleepDuration;

    public static Task init(Message message, SleepDuration sleepDuration) {
        var errors = new Errors();
        if (message == null) {
            errors.addError(TaskError.errMessageIsRequired());
        }
        if (sleepDuration == null) {
            errors.addError(TaskError.errSleepDurationIsRequired());
        }
        if (errors.isPresent()) {
            throw errors.toException();
        }

        var task = new Task();
        task.id = TaskId.init();
        task.message = message;
        task.sleepDuration = sleepDuration;

        return task;
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }
}
