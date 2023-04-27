package ru.strict.controltime.task.integration.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetAllTasksTest extends TaskJsonRepositoryBaseTest {

    @Test
    void testGetAllTasks_ValidTask_NoError() {
        var expectedTask = TaskStub.getTask();
        var expectedAllTasks = List.of(savedTask, expectedTask);

        taskRepository.insert(expectedTask);

        var actualAllTasks = taskRepository.getAllTasks();

        assertEquals(expectedAllTasks.size(), actualAllTasks.size());
        assertTrue(expectedAllTasks.containsAll(actualAllTasks));
    }
}
