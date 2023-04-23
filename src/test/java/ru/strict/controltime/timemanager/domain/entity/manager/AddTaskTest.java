package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskError;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class AddTaskTest {

    @Test
    void testAddTask_WithoutParams_ThrowException() {
        var timeManager = TimeManagerStub.getEmptyTimeManager();

        try {
            timeManager.addTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_ValidParams_NoError() {
        var timeManager = TimeManagerStub.getEmptyTimeManager();

        var expectedTask1 = TaskStub.getTask();
        var expectedTask2 = TaskStub.getTask();
        var expectedTasks = List.of(expectedTask1, expectedTask2);

        timeManager.addTask(expectedTask1);
        timeManager.addTask(expectedTask2);

        var actualTasks = timeManager.getTasks();
        assertEquals(2, actualTasks.size());

        var actualTasksMapByMessage = actualTasks.stream().
                collect(Collectors.toMap(Task::getMessage, Function.identity()));
        expectedTasks.forEach(taskParams -> {
            var actualTask = actualTasksMapByMessage.get(taskParams.getMessage());
            assertNotNull(actualTask);
            assertEquals(taskParams.getSleepDuration(), actualTask.getSleepDuration());
        });
    }
}
