package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

class MarkAsProcessedTest {

    @Test
    void testMarkAsProcessed_NotReady_LastProcessedAtIsNull_ThrowException() {
        var task = TaskStub.getBaseTask();

        try {
            task.markAsProcessed();
        } catch (CodeableException ex) {
            ex.equalsByCode(TaskError.taskIsNotReadyErrorCode);

            assertFalse(task.getLastProcessedAt().isPresent());
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkAsProcessed_IsReady_LastProcessedAtIsNull_UpdateDate() {
        var task = TaskStub.getReadyBaseTask();
        assertFalse(task.getLastProcessedAt().isPresent());

        assertDoesNotThrow(task::markAsProcessed);
        assertTrue(task.getLastProcessedAt().isPresent());
    }

    @Test
    void testMarkAsProcessed_NotReady_LastProcessedAtNotNull_ThrowException() {
        var task = TaskStub.getFullTask();
        var originalLastProcessedAt = task.getLastProcessedAt().orElseThrow();

        try {
            task.markAsProcessed();
        } catch (CodeableException ex) {
            ex.equalsByCode(TaskError.taskIsNotReadyErrorCode);

            assertEquals(originalLastProcessedAt, task.getLastProcessedAt().orElseThrow());
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkAsProcessed_IsReady_LastProcessedAtNotNull_UpdateDate() {
        var task = TaskStub.getReadyFullTask();
        var originalLastProcessedAt = task.getLastProcessedAt().orElseThrow();

        assertDoesNotThrow(task::markAsProcessed);
        assertNotEquals(originalLastProcessedAt, task.getLastProcessedAt().orElseThrow());
    }
}
