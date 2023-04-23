package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class MarkTaskAsProcessedTest {

    @Test
    void testMarkTaskAsProcessed_TaskIdIsNull_ThrowException() {
        var timeManager = TimeManagerStub.getFullTimeManager();

        try {
            timeManager.markTaskAsProcessed(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
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
        var expectedReadyTask = ManageTaskStub.getReadyBaseManageTask();
        var tasksList = List.of(
                ManageTaskStub.getBaseManageTask(),
                ManageTaskStub.getFullManageTask(),
                expectedReadyTask,
                ManageTaskStub.getReadyFullManageTask()
        );

        var timeManager = TimeManagerStub.getTimeManagerFrom(tasksList);
        assertFalse(expectedReadyTask.getLastProcessedAt().isPresent());

        timeManager.markTaskAsProcessed(expectedReadyTask.getTaskId());

        assertTrue(expectedReadyTask.getLastProcessedAt().isPresent());
    }
}
