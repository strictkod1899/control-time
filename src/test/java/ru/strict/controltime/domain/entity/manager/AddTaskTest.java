package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AddTaskTest {

    @Test
    void testAddTask_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskError.messageIsRequiredErrorCode,
                TaskError.sleepDurationIsRequiredErrorCode
        );

        var timeManager = TimeManagerStub.getBaseTimeManager();

        try {
            timeManager.addTask(null, null);
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_ValidParams_NoError() {
        var timeManager = TimeManagerStub.getBaseTimeManager();

        var taskParams1 = new AddTaskParams(){{
            message = TaskStub.getMessage();
            sleepDuration = Duration.ofMinutes(30);
        }};
        var taskParams2 = new AddTaskParams(){{
            message = TaskStub.getMessage();
            sleepDuration = Duration.ofMinutes(10);
        }};
        var expectedTasksParams = List.of(taskParams1, taskParams2);

        timeManager.addTask(taskParams1.message, taskParams1.sleepDuration);
        timeManager.addTask(taskParams2.message, taskParams2.sleepDuration);

        var actualTasks = timeManager.getTasks();
        assertEquals(2, actualTasks.size());

        var actualTasksMapByMessage = actualTasks.stream().
                collect(Collectors.toMap(Task::getMessage, Function.identity()));
        expectedTasksParams.forEach(taskParams -> {
            var actualTask = actualTasksMapByMessage.get(taskParams.message);
            assertNotNull(actualTask);
            assertEquals(taskParams.sleepDuration, actualTask.getSleepDuration());
        });
    }

    private static class AddTaskParams {
        Message message;
        Duration sleepDuration;
    }
}
