package ru.strict.controltime.task.integration.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetByIdTest extends TaskJsonRepositoryBaseTest {

    @Test
    void testGetById_ValidTask_NoError() {
        var actualTask = taskRepository.getById(savedTask.getId());

        assertTrue(actualTask.isPresent());
        assertEquals(savedTask, actualTask.get());
    }
}
