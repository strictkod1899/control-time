package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TasksFromTest {

    @Test
    void testFrom_TasksListIsNull_ThrowException() {
        try {
            Tasks.from(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.tasksListIsRequiredErrorCode));

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_TasksListIsEmpty_ReturnTasks() {
        var tasks = Tasks.from(List.of());
        assertTrue(tasks.toCollection().isEmpty());
    }

    @Test
    void testFrom_DoubledTasks_ThrowException() {
        var task1 = TaskStub.getBaseTask();
        var task2 = TaskStub.getFullTaskWithId(task1.getId());

        try {
            Tasks.from(List.of(task1, task2));
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.doubledTaskErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_ValidTasksList_ReturnTasks() {
        var task1 = TaskStub.getBaseTask();
        var task2 = TaskStub.getFullTask();
        var expectedTasks = List.of(task1, task2);

        var tasks = Tasks.from(expectedTasks);

        var actualTasks = tasks.toCollection();
        assertEquals(2, actualTasks.size());

        var actualTasksMapById = actualTasks.stream().
                collect(Collectors.toMap(Task::getId, Function.identity()));
        expectedTasks.forEach(expectedTask -> {
            var actualTask = actualTasksMapById.get(expectedTask.getId());
            assertEquals(expectedTask, actualTask);
        });
    }
}
