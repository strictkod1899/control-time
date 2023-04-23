package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Execution(ExecutionMode.CONCURRENT)
class TaskInitTest {

    @Test
    void testInit_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode);

        try {
            Task.init(null, null);
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        new FailTestException();
    }

    @Test
    void testInit_AllValidParams_ReturnTask() {
        var expectedMessage = TaskStub.getMessage();
        var expectedSleepDuration = TaskStub.getSleepDuration();

        var task = Task.init(expectedMessage, expectedSleepDuration);

        assertNotNull(task);

        assertNotNull(task.getId());
        assertEquals(expectedMessage, task.getMessage());
        assertEquals(expectedSleepDuration, task.getSleepDuration());
    }
}
