package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.ErrorsException;
import ru.strict.test.AssertUtil;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class ManageTaskBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(ManageTaskError.taskIsRequiredErrorCode,
                ManageTaskError.taskStartedAtIsRequiredErrorCode);

        var actualEx = assertThrows(ErrorsException.class, () -> ManageTask.builder().build());

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
    }

    @Test
    void testBuild_RequiredParams_ReturnManageTask() {
        var expectedTask = TaskStub.getTask();
        var expectedStartedAt = Instant.ofEpochMilli(1676045473477L);

        var manageTask = ManageTask.builder().
                task(expectedTask).
                startedAt(expectedStartedAt).
                build();

        assertNotNull(manageTask);

        assertEquals(expectedTask, manageTask.getTask());
        assertEquals(expectedStartedAt, manageTask.getStartedAt());
        assertFalse(manageTask.getLastProcessedAt().isPresent());
    }

    @Test
    void testBuild_AllValidParams_ReturnManageTask() {
        var expectedTask = TaskStub.getTask();
        var expectedStartedAt = Instant.ofEpochMilli(1676045473477L);
        var expectedLastProcessedAt = Instant.ofEpochMilli(1676047473477L);

        var manageTask = ManageTask.builder().
                task(expectedTask).
                startedAt(expectedStartedAt).
                lastProcessedAt(expectedLastProcessedAt).
                build();

        assertNotNull(manageTask);

        assertEquals(expectedTask, manageTask.getTask());
        assertEquals(expectedStartedAt, manageTask.getStartedAt());
        assertTrue(manageTask.getLastProcessedAt().isPresent());
        assertEquals(expectedLastProcessedAt, manageTask.getLastProcessedAt().get());
    }
}
