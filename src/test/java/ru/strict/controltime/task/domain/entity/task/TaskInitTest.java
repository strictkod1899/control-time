package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.exception.ErrorsException;
import ru.strict.test.AssertUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TaskInitTest {

    @Test
    void testInit_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode
        );

        var actualEx = assertThrows(ErrorsException.class, () -> Task.init(null, null));

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
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
