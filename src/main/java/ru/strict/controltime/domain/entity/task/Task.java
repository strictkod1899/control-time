package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PACKAGE)
public class Task {
    TaskId id;
    Message message;
    Duration sleepDuration;
    Instant startedAt;
    Instant lastProcessedAt;

    public void markAsProcessed() {
        if (!isReady()) {
            throw TaskError.errTaskIsNotReady();
        }
        lastProcessedAt = Instant.now();
    }

    public boolean isReady() {
        var lastStartedAt = getLastProcessedAt().orElse(startedAt);
        var requiredEndSleep = lastStartedAt.plus(sleepDuration);

        return requiredEndSleep.isBefore(Instant.now());
    }

    public Optional<Instant> getLastProcessedAt() {
        return Optional.ofNullable(lastProcessedAt);
    }
}
