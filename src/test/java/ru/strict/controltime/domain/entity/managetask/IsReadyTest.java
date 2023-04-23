package ru.strict.controltime.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class IsReadyTest {

    @Test
    void testIsReady_NewTask_ReturnFalse() {
        var manageTask = ManageTaskStub.getBaseManageTask();

        assertFalse(manageTask.isReady());
    }

    @Test
    void testIsReady_LastProcessedIsNull_DurationIsExpired_ReturnTrue() {
        var givenDuration = TaskStub.getSleepDurationFrom(Duration.ofMinutes(20));
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        var task = TaskStub.getTaskBuilder().sleepDuration(givenDuration).build();

        var manageTask = ManageTaskStub.getBaseManageTaskBuilder().
                task(task).
                startedAt(givenStartedAt).
                build();

        assertTrue(manageTask.isReady());
    }

    @Test
    void testIsReady_LastProcessedNotNull_DurationIsActive_ReturnFalse() {
        var givenDuration = TaskStub.getSleepDurationFrom(Duration.ofMinutes(20));
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes(), ChronoUnit.MINUTES);

        var task = TaskStub.getTaskBuilder().sleepDuration(givenDuration).build();

        var manageTask = ManageTaskStub.getBaseManageTaskBuilder().
                task(task).
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                build();

        assertFalse(manageTask.isReady());
    }

    @Test
    void testIsReady_LastProcessedNotNull_DurationIsExpired_ReturnTrue() {
        var givenDuration = TaskStub.getSleepDurationFrom(Duration.ofMinutes(20));
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()*2+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        var task = TaskStub.getTaskBuilder().sleepDuration(givenDuration).build();

        var manageTask = ManageTaskStub.getBaseManageTaskBuilder().
                task(task).
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                build();

        assertTrue(manageTask.isReady());
    }
}
