package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetReadyTaskIdsTest {

    @Test
    void testGetReadyTaskIds_EmptyTasks_ReturnEmptyList() {
        var timeManager = TimeManagerStub.getBaseTimeManager();

        var readyTaskIds = timeManager.getReadyTaskIds();

        assertTrue(readyTaskIds.isEmpty());
    }

    @Test
    void testGetReadyTaskIds_WithoutReadyTasks_ReturnEmptyList() {
        var timeManager = TimeManagerStub.getTimeManagerWithoutReadyTasks();

        var readyTaskIds = timeManager.getReadyTaskIds();

        assertTrue(readyTaskIds.isEmpty());
    }

    @Test
    void testGetReadyTaskIds_WithReadyTasks_ReturnNotEmptyList() {
        var expectedReadyTask1 = TaskStub.getReadyBaseTask();
        var expectedReadyTask2 = TaskStub.getReadyFullTask();
        var expectedReadyTasksIds = List.of(expectedReadyTask1.getId(), expectedReadyTask2.getId());

        var givenTasks = List.of(
                TaskStub.getBaseTask(),
                expectedReadyTask2,
                TaskStub.getFullTask(),
                expectedReadyTask1
        );
        var timeManager = TimeManagerStub.getTimeManagerFrom(givenTasks);

        var actualReadyTaskIds = timeManager.getReadyTaskIds();

        assertFalse(actualReadyTaskIds.isEmpty());
        assertEquals(expectedReadyTasksIds.size(), actualReadyTaskIds.size());
        assertTrue(actualReadyTaskIds.containsAll(expectedReadyTasksIds));
    }
}
