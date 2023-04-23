package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class NewTimeManagerTest {

    @Test
    void testInit_TasksIsNull_ThrowException() {
        try {
            TimeManager.init(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskListIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
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
