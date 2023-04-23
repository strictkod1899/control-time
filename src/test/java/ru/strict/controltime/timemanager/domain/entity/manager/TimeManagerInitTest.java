package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TimeManagerInitTest {

    @Test
    void testInit_TasksIsNull_ThrowException() {
        var actualEx = assertThrows(CodeableException.class, () -> TimeManager.init(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskListIsRequiredErrorCode));
    }

    @Test
    void testInit_TasksIsEmpty_ReturnManager() {
        var timeManager = TimeManager.init(List.of());

        assertTrue(timeManager.getManageTasks().isEmpty());
    }

    @Test
    void testInit_ValidParams_ReturnManager() {
        var expectedTasks = List.of(
                TaskStub.getTask(),
                TaskStub.getTask()
        );

        var timeManager = TimeManager.init(expectedTasks);

        assertEquals(expectedTasks.size(), timeManager.getTasks().size());
        assertTrue(timeManager.getTasks().containsAll(expectedTasks));

        assertEquals(2, timeManager.getManageTasks().size());
    }
}
