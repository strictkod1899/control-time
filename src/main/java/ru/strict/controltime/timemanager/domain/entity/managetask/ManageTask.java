package ru.strict.controltime.timemanager.domain.entity.managetask;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.time.Instant;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PACKAGE)
public class ManageTask {
    Task task;
    Instant startedAt;
    Instant lastProcessedAt;

    public static ManageTask init(Task task) {
        return ManageTask.builder().
                task(task).
                startedAt(Instant.now()).
                build();
    }

    public void markAsProcessed() {
        if (!isReady()) {
            throw ManageTaskError.errTaskIsNotReady();
        }
        lastProcessedAt = Instant.now();
    }

    public boolean isReady() {
        var lastStartedAt = getLastProcessedAt().orElse(startedAt);
        var requiredEndSleep = lastStartedAt.plusNanos(task.getSleepDuration().toNanos());

        return requiredEndSleep.isBefore(Instant.now());
    }

    public TaskId getTaskId() {
        return task.getId();
    }

    public Optional<Instant> getLastProcessedAt() {
        return Optional.ofNullable(lastProcessedAt);
    }

    public static ManageTaskBuilder builder() {
        return new ManageTaskBuilder();
    }
}
