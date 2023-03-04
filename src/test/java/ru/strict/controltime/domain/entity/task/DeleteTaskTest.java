package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var tasks = TaskStub.getFullTasks();

        try {
            tasks.deleteTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var tasks = TaskStub.getFullTasks();
        var originalTasksCount = tasks.toCollection().size();
        var taskId = TaskStub.getId();

        try {
            tasks.deleteTask(taskId);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskNotFoundErrorCode));

            var actualTasksCount = tasks.toCollection().size();
            assertEquals(originalTasksCount, actualTasksCount);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_ValidParams_NoError() {
        var tasks = TaskStub.getFullTasks();
        var taskForRemove = tasks.toCollection().iterator().next();

        tasks.deleteTask(taskForRemove.getId());

        assertFalse(tasks.getTaskById(taskForRemove.getId()).isPresent());
    }
}
