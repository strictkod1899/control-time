package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.deleteTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var originalTasksCount = timeManager.getManageTasks().size();
        var taskId = TaskStub.getId();

        try {
            timeManager.deleteTask(taskId);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskNotFoundErrorCode));

            var actualTasksCount = timeManager.getManageTasks().size();
            assertEquals(originalTasksCount, actualTasksCount);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_ValidParams_NoError() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var taskForRemove = timeManager.getTasks().iterator().next();

        timeManager.deleteTask(taskForRemove.getId());

        assertFalse(timeManager.getTaskMessage(taskForRemove.getId()).isPresent());
    }
}
