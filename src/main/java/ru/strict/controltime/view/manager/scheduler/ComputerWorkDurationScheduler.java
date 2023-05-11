package ru.strict.controltime.view.manager.scheduler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.manager.TimeManagerViewController;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComputerWorkDurationScheduler {

    TimeManagerViewController timeManagerViewController;
    Instant startedAt;

    public ComputerWorkDurationScheduler(TimeManagerViewController timeManagerViewController) {
        Objects.requireNonNull(timeManagerViewController, "timeManagerViewController is required");

        this.timeManagerViewController = timeManagerViewController;
        startedAt = Instant.now();
    }

    public void refreshWorkDuration() {
        var computerWorkDurationInstant = Instant.now().minusMillis(startedAt.toEpochMilli());
        var computerWorkDuration =Duration.ofMillis(computerWorkDurationInstant.toEpochMilli());

        timeManagerViewController.refreshComputerWorkDuration(computerWorkDuration);
    }
}
