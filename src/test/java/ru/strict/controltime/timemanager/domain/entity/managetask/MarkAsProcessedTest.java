package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class MarkAsProcessedTest {

    @Test
    void testMarkAsProcessed_NotReady_LastProcessedAtIsNull_ThrowException() {
        var manageTask = ManageTaskStub.getBaseManageTask();

        var actualEx = assertThrows(CodeableException.class, () -> manageTask.markAsProcessed());

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIsNotReadyErrorCode));

        assertFalse(manageTask.getLastProcessedAt().isPresent());
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

        var actualEx = assertThrows(CodeableException.class, () -> manageTask.markAsProcessed());

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIsNotReadyErrorCode));

        assertEquals(originalLastProcessedAt, manageTask.getLastProcessedAt().orElseThrow());
    }

    @Test
    void testMarkAsProcessed_IsReady_LastProcessedAtNotNull_UpdateDate() {
        var manageTask = ManageTaskStub.getReadyFullManageTask();
        var originalLastProcessedAt = manageTask.getLastProcessedAt().orElseThrow();

        assertDoesNotThrow(manageTask::markAsProcessed);
        assertNotEquals(originalLastProcessedAt, manageTask.getLastProcessedAt().orElseThrow());
    }
}
