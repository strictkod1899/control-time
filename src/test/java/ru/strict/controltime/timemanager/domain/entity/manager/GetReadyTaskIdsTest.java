package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class GetReadyTaskIdsTest {

    @Test
    void testGetReadyTaskIds_EmptyTasks_ReturnEmptyList() {
        var timeManager = TimeManagerStub.getEmptyTimeManager();

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
        var expectedReadyTask1 = ManageTaskStub.getReadyBaseManageTask();
        var expectedReadyTask2 = ManageTaskStub.getReadyFullManageTask();
        var expectedReadyTasksIds = List.of(expectedReadyTask1.getTaskId(), expectedReadyTask2.getTaskId());

        var givenTasks = List.of(
                ManageTaskStub.getBaseManageTask(),
                expectedReadyTask2,
                ManageTaskStub.getFullManageTask(),
                expectedReadyTask1
        );
        var timeManager = TimeManagerStub.getTimeManagerFrom(givenTasks);

        var actualReadyTaskIds = timeManager.getReadyTaskIds();

        assertFalse(actualReadyTaskIds.isEmpty());
        assertEquals(expectedReadyTasksIds.size(), actualReadyTaskIds.size());
        assertTrue(actualReadyTaskIds.containsAll(expectedReadyTasksIds));
    }
}
