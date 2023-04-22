package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.domain.entity.managetask.ManageTask;
import ru.strict.controltime.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class TimeManagerFromTest {

    @Test
    void testInit_IdIsNull_ThrowException() {
        try {
            TimeManager.from(null, List.of());
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TimeManagerError.timeManagerIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testInit_TasksIsNull_ReturnManager() {
        var givenId = TimeManagerStub.getId();

        try {
            TimeManager.from(givenId, null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.manageTaskListIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
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
