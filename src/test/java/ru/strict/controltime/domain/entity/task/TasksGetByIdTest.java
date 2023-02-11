package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

class TasksGetByIdTest {

    @Test
    void testGetById_TaskIdIsNull_ThrowException() {
        var tasks = TaskStub.getFullTasks();

        try {
            tasks.getTaskById(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testGetById_TaskNotFound_ReturnNull() {
        var tasks = TaskStub.getFullTasks();

        var task = tasks.getTaskById(TaskStub.getId());

        assertFalse(task.isPresent());
    }

    @Test
    void testGetById_ValidTaskId_ReturnTask() {
        var tasks = TaskStub.getFullTasks();
        var expectedTask = tasks.toCollection().iterator().next();

        var task = tasks.getTaskById(expectedTask.getId());

        assertTrue(task.isPresent());
        assertEquals(expectedTask, task.get());
    }
}
