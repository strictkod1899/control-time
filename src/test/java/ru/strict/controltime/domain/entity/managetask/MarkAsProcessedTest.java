package ru.strict.controltime.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class MarkAsProcessedTest {

    @Test
    void testMarkAsProcessed_NotReady_LastProcessedAtIsNull_ThrowException() {
        var manageTask = ManageTaskStub.getBaseManageTask();

        try {
            manageTask.markAsProcessed();
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIsNotReadyErrorCode));

            assertFalse(manageTask.getLastProcessedAt().isPresent());
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkAsProcessed_IsReady_LastProcessedAtIsNull_UpdateDate() {
        var manageTask = ManageTaskStub.getReadyBaseManageTask();
        assertFalse(manageTask.getLastProcessedAt().isPresent());

        assertDoesNotThrow(manageTask::markAsProcessed);
        assertTrue(manageTask.getLastProcessedAt().isPresent());
    }

    @Test
    void testMarkAsProcessed_NotReady_LastProcessedAtNotNull_ThrowException() {
        var manageTask = ManageTaskStub.getFullManageTask();
        var originalLastProcessedAt = manageTask.getLastProcessedAt().orElseThrow();

        try {
            manageTask.markAsProcessed();
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIsNotReadyErrorCode));

            assertEquals(originalLastProcessedAt, manageTask.getLastProcessedAt().orElseThrow());
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkAsProcessed_IsReady_LastProcessedAtNotNull_UpdateDate() {
        var manageTask = ManageTaskStub.getReadyFullManageTask();
        var originalLastProcessedAt = manageTask.getLastProcessedAt().orElseThrow();

        assertDoesNotThrow(manageTask::markAsProcessed);
        assertNotEquals(originalLastProcessedAt, manageTask.getLastProcessedAt().orElseThrow());
    }
}
