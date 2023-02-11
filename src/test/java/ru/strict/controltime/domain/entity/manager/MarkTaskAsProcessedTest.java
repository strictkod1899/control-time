package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarkTaskAsProcessedTest {

    @Test
    void testMarkTaskAsProcessed_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.markTaskAsProcessed(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkTaskAsProcessed_TaskNotExists_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.markTaskAsProcessed(TaskStub.getId());
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TimeManagerError.taskNotFoundErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testMarkTaskAsProcessed_ValidParams_NoError() {
        var expectedReadyTask = TaskStub.getReadyBaseTask();
        var tasksList = List.of(
                TaskStub.getBaseTask(),
                TaskStub.getFullTask(),
                expectedReadyTask,
                TaskStub.getReadyFullTask()
        );

        var timeManager = TimeManagerStub.getTimeManagerFrom(tasksList);
        assertFalse(expectedReadyTask.getLastProcessedAt().isPresent());

        timeManager.markTaskAsProcessed(expectedReadyTask.getId());

        assertTrue(expectedReadyTask.getLastProcessedAt().isPresent());
    }
}
