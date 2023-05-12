package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class GetTaskTest {

    @Test
    void testGetTask_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManager.getTask(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
    }

    @Test
    void testGetTask_TaskNotFound_ReturnNull() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        var task = timeManager.getTask(TaskStub.getId());

        assertFalse(task.isPresent());
    }

    @Test
    void testGetTask_ValidParams_Return() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var expectedTask = timeManager.getTasks().iterator().next();

        var task = timeManager.getTask(expectedTask.getId());

        assertTrue(task.isPresent());
        assertEquals(expectedTask, task.get());
    }
}
