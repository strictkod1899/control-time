package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeManagerFromTest {

    @Test
    void testFrom_IdIsNull_ThrowException() {
        try {
            TimeManager.from(null, null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TimeManagerError.timeManagerIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_TasksIsNull_ThrowException() {
        try {
            TimeManager.from(TimeManagerStub.getId(), null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.tasksListIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_TasksIsEmpty_ReturnManager() {
        var timeManager = TimeManager.from(TimeManagerStub.getId(), List.of());

        assertTrue(timeManager.getTasks().isEmpty());
    }

    @Test
    void testFrom_ValidParams_ReturnManager() {
        var expectedTasks = List.of(
                TaskStub.getBaseTask(),
                TaskStub.getFullTask()
        );

        var timeManager = TimeManager.from(TimeManagerStub.getId(), expectedTasks);

        assertEquals(expectedTasks.size(), timeManager.getTasks().size());
        assertTrue(timeManager.getTasks().containsAll(expectedTasks));
    }
}
