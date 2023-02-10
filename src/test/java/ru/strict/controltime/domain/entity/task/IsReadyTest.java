package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class IsReadyTest {

    @Test
    void testIsReady_NewTask_ReturnFalse() {
        var task = TaskStub.getBaseTask();

        assertFalse(task.isReady());
    }

    @Test
    void testIsReady_LastProcessedIsNull_DurationIsExpired_ReturnTrue() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        var task = TaskStub.getBaseTaskBuilder().
                startedAt(givenStartedAt).
                sleepDuration(givenDuration).
                build();

        assertTrue(task.isReady());
    }

    @Test
    void testIsReady_LastProcessedNotNull_DurationIsActive_ReturnFalse() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes(), ChronoUnit.MINUTES);

        var task = TaskStub.getBaseTaskBuilder().
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                sleepDuration(givenDuration).
                build();

        assertFalse(task.isReady());
    }

    @Test
    void testIsReady_LastProcessedNotNull_DurationIsExpired_ReturnTrue() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()*2+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        var task = TaskStub.getBaseTaskBuilder().
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                sleepDuration(givenDuration).
                build();

        assertTrue(task.isReady());
    }
}
