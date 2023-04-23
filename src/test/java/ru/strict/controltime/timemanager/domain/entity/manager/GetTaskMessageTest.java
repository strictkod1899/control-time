package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.domain.entity.task.TaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class GetTaskMessageTest {

    @Test
    void testGetTaskMessage_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManager.getTaskMessage(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
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
