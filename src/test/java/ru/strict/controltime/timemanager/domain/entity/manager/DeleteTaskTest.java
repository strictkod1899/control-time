package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManager.deleteTask(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var originalTasksCount = timeManager.getManageTasks().size();
        var taskId = TaskStub.getId();

        var actualEx = assertThrows(CodeableException.class, () -> timeManager.deleteTask(taskId));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskNotFoundErrorCode));

        var actualTasksCount = timeManager.getManageTasks().size();
        assertEquals(originalTasksCount, actualTasksCount);
    }

    @Test
    void testDeleteTask_ValidParams_NoError() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var taskForRemove = timeManager.getTasks().iterator().next();

        timeManager.deleteTask(taskForRemove.getId());

        assertFalse(timeManager.getTaskMessage(taskForRemove.getId()).isPresent());
    }
}
