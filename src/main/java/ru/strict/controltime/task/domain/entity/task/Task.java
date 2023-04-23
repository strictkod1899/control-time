package ru.strict.controltime.task.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.strict.exception.Errors;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PACKAGE)
public class Task {
    TaskId id;
    Message message;
    SleepDuration sleepDuration;

    public static Task init(Message message, SleepDuration sleepDuration) {
        return builder().
                id(TaskId.init()).
                message(message).
                sleepDuration(sleepDuration).
                build();
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }
}
