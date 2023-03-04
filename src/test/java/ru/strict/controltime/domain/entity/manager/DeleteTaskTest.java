package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.deleteTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();
        var originalTasksCount = timeManager.getTasks().size();
        var taskId = TaskStub.getId();

        try {
            timeManager.deleteTask(taskId);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskNotFoundErrorCode));

            var actualTasksCount = timeManager.getTasks().size();
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
