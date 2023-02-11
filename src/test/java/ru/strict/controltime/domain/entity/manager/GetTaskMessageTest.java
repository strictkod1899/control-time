package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

class GetTaskMessageTest {

    @Test
    void testGetTaskMessage_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.getTaskMessage(null);
        } catch (CodeableException ex) {
            ex.equalsByCode(TaskError.taskIdIsRequiredErrorCode);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testGetTaskMessage_TaskNotFound_ReturnNull() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        var message = timeManager.getTaskMessage(TaskStub.getId());

        assertFalse(message.isPresent());
    }

    @Test
    void testGetTaskMessage_ValidParams_ReturnMessage() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var expectedTask = timeManager.getTasks().iterator().next();

        var message = timeManager.getTaskMessage(expectedTask.getId());

        assertTrue(message.isPresent());
        assertEquals(expectedTask.getMessage(), message.get());
    }
}
