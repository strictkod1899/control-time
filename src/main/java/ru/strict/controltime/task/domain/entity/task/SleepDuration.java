package ru.strict.controltime.task.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.Duration;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SleepDuration {
    private static final long MIN_DURATION_SECONDS = 30;

    Duration value;

    public static SleepDuration from(Duration duration) {
        return new SleepDuration(duration);
    }

    private SleepDuration(Duration duration) {
        if (duration == null) {
            throw TaskError.errDurationIsRequired();
        }
        if (duration.toSeconds() < MIN_DURATION_SECONDS) {
            throw TaskError.errSleepDurationIsTooSmall();
        }

        this.value = duration;
    }

    public long toMinutes() {
        return value.toMinutes();
    }

    public Duration toDuration() {
        return value;
    }

    public long toMillis() {
        return value.toMillis();
    }

    public long toNanos() {
        return value.toNanos();
    }
}
