package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

class AddTaskTest {

    @Test
    void testAddTask_TaskIsNull_ThrowException() {
        var tasks = TaskStub.getFullTasks();

        try {
            tasks.addTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_DoubledTask_ThrowException() {
        var tasks = TaskStub.getFullTasks();
        var existsTask = tasks.toCollection().iterator().next();
        var givenNewTask = TaskStub.getFullTaskWithId(existsTask.getId());

        try {
            tasks.addTask(givenNewTask);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.doubledTaskErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_ValidTask_NoError() {
        var tasks = TaskStub.getFullTasks();
        var originalTasksSize = tasks.toCollection().size();

        var expectedNewTask = TaskStub.getFullTask();

        tasks.addTask(expectedNewTask);

        var actualTasksSize = tasks.toCollection().size();
        assertEquals(originalTasksSize+1, actualTasksSize);

        var actualTask = tasks.getTaskById(expectedNewTask.getId());
        assertTrue(actualTask.isPresent());
        assertEquals(expectedNewTask, actualTask.get());
    }
}
