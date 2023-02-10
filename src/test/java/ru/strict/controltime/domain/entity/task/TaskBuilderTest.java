package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(TaskError.taskIdIsRequiredErrorCode,
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode);

        try {
            new TaskBuilder().build();
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        new FailTestException();
    }

    @Test
    void testBuild_RequiredParams_ReturnTask() {
        var expectedTaskId = TaskStub.getTaskId();
        var expectedMessage = TaskStub.getMessage();
        var expectedSleepDuration = Duration.ofMinutes(30);

        var task = new TaskBuilder().
                id(expectedTaskId).
                message(expectedMessage).
                sleepDuration(expectedSleepDuration).
                build();

        assertNotNull(task);

        assertEquals(expectedTaskId, task.getId());
        assertEquals(expectedMessage, task.getMessage());
        assertEquals(expectedSleepDuration, task.getSleepDuration());
        assertFalse(task.getLastProcessedAt().isPresent());
        assertNotNull(task.getStartedAt());
    }

    @Test
    void testBuild_AllValidParams_ReturnTask() {
        var expectedTaskId = TaskStub.getTaskId();
        var expectedMessage = TaskStub.getMessage();
        var expectedSleepDuration = Duration.ofMinutes(30);
        var expectedStartedAt = Instant.ofEpochMilli(1676045473477L);
        var expectedLastProcessedAt = Instant.ofEpochMilli(1676047473477L);

        var task = new TaskBuilder().
                id(expectedTaskId).
                message(expectedMessage).
                sleepDuration(expectedSleepDuration).
                startedAt(expectedStartedAt).
                lastProcessedAt(expectedLastProcessedAt).
                build();

        assertNotNull(task);

        assertEquals(expectedTaskId, task.getId());
        assertEquals(expectedMessage, task.getMessage());
        assertEquals(expectedSleepDuration, task.getSleepDuration());
        assertTrue(task.getLastProcessedAt().isPresent());
        assertEquals(expectedLastProcessedAt, task.getLastProcessedAt().get());
        assertEquals(expectedStartedAt, task.getStartedAt());
    }
}
