package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TaskBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(TaskError.taskIdIsRequiredErrorCode,
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode);

        try {
            Task.builder().build();
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        new FailTestException();
    }

    @Test
    void testBuild_AllValidParams_ReturnTask() {
        var expectedTaskId = TaskStub.getId();
        var expectedMessage = TaskStub.getMessage();
        var expectedSleepDuration = Duration.ofMinutes(30);

        var task = Task.builder().
                id(expectedTaskId).
                message(expectedMessage).
                sleepDuration(expectedSleepDuration).
                build();

        assertNotNull(task);

        assertEquals(expectedTaskId, task.getId());
        assertEquals(expectedMessage, task.getMessage());
        assertEquals(expectedSleepDuration, task.getSleepDuration());
    }
}
