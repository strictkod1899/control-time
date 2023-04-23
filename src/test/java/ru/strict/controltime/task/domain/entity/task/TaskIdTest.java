package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
        var actualEx = assertThrows(CodeableException.class, () -> TaskId.from(null));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));
    }

    @Test
    void testIdFrom_EmptyString_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> TaskId.from(""));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));
    }

    @Test
    void testIdFrom_NotUUID_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> TaskId.from("123"));

        assertTrue(actualEx.equalsByCode(EntityIdError.invalidIdFormatErrorCode));
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
