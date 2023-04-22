package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class TaskIdTest {

    @Test
    void testNewId_NoError() {
        var taskId1 = TaskId.init();
        var taskId2 = TaskId.init();

        assertNotEquals(taskId1, taskId2);
    }

    @Test
    void testIdFrom_StringIsNull_ThrowError() {
        try {
            TaskId.from(null);
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.idIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testIdFrom_EmptyString_ThrowError() {
        try {
            TaskId.from("");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.idIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testIdFrom_NotUUID_ThrowError() {
        try {
            TaskId.from("123");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.invalidIdFormatErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testIdFrom_ValidParam_NoError() {
        var expectedIdStr = UUID.randomUUID().toString();

        var taskId1 = TaskId.from(expectedIdStr);
        var taskId2 = TaskId.from(expectedIdStr);

        assertEquals(taskId1, taskId2);
        assertEquals(taskId1.toString(), taskId2.toString());
    }
}
