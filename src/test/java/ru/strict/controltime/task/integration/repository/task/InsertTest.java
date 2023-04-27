package ru.strict.controltime.task.integration.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;

import static org.junit.jupiter.api.Assertions.*;

class InsertTest extends TaskJsonRepositoryBaseTest {

    @Test
    void testInsert_ValidTask_NoError() {
        var expectedTask = TaskStub.getTask();

        taskRepository.insert(expectedTask);

        var actualTask = taskRepository.getById(expectedTask.getId());

        assertTrue(actualTask.isPresent());
        assertEquals(expectedTask, actualTask.get());
    }
}
