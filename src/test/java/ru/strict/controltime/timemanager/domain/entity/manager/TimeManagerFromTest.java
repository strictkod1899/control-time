package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TimeManagerFromTest {

    @Test
    void testInit_IdIsNull_ThrowException() {
        var actualEx = assertThrows(CodeableException.class, () -> TimeManager.from(null, List.of()));

        assertTrue(actualEx.equalsByCode(TimeManagerError.timeManagerIdIsRequiredErrorCode));
    }

    @Test
    void testInit_TasksIsNull_ReturnManager() {
        var givenId = TimeManagerStub.getId();

        var actualEx = assertThrows(CodeableException.class, () -> TimeManager.from(givenId, null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.manageTaskListIsRequiredErrorCode));
    }

    @Test
    void testInit_TasksIsEmpty_ReturnManager() {
        var expectedId = TimeManagerStub.getId();

        var timeManager = TimeManager.from(expectedId, List.of());

        assertEquals(expectedId, timeManager.getId());
        assertTrue(timeManager.getManageTasks().isEmpty());
    }

    @Test
    void testInit_ValidParams_ReturnManager() {
        var expectedId = TimeManagerStub.getId();
        var expectedManageTasks = ManageTaskStub.getFullManageTasks().toList();
        var expectedTasks = expectedManageTasks.stream().map(ManageTask::getTask).toList();

        var timeManager = TimeManager.from(expectedId, expectedManageTasks);

        assertEquals(expectedId, timeManager.getId());
        assertEquals(expectedManageTasks.size(), timeManager.getManageTasks().size());
        assertTrue(timeManager.getManageTasks().containsAll(expectedManageTasks));

        assertEquals(expectedManageTasks.size(), timeManager.getTasks().size());
        assertTrue(expectedTasks.containsAll(timeManager.getTasks()));
    }
}
