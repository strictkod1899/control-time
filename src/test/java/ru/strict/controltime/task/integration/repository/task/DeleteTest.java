package ru.strict.controltime.task.integration.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;

import static org.junit.jupiter.api.Assertions.*;

class DeleteTest extends TaskJsonRepositoryBaseTest {

    @Test
    void testDelete_ValidTask_NoError() {
        var expectedTask = TaskStub.getTask();

        taskRepository.insert(expectedTask);

        taskRepository.delete(expectedTask.getId());

        var actualTask = taskRepository.getById(expectedTask.getId());
        assertFalse(actualTask.isPresent());

        var actualSavedTask = taskRepository.getById(savedTask.getId());
        assertTrue(actualSavedTask.isPresent());
        assertEquals(savedTask, actualSavedTask.get());
    }
}
