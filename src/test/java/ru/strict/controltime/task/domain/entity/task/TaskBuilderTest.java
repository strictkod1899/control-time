package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.ErrorsException;
import ru.strict.test.AssertUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TaskBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskError.taskIdIsRequiredErrorCode,
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode);

        var actualEx = assertThrows(ErrorsException.class, () -> Task.builder().build());

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
    }

    @Test
    void testBuild_AllValidParams_ReturnTask() {
        var expectedTaskId = TaskStub.getId();
        var expectedMessage = TaskStub.getMessage();
        var expectedSleepDuration = TaskStub.getSleepDuration();

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
