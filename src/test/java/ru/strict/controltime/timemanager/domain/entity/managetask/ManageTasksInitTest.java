package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class ManageTasksInitTest {

    @Test
    void testInit_ManageTasksListIsNull_ThrowException() {
        var actualEx = assertThrows(CodeableException.class, () -> ManageTasks.init(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskListIsRequiredErrorCode));
    }

    @Test
    void testInit_ManageTasksListIsEmpty_ReturnManageTasks() {
        var manageTasks = ManageTasks.init(List.of());
        assertTrue(manageTasks.toList().isEmpty());
    }

    @Test
    void testInit_DoubledManageTasks_ThrowException() {
        var task1 = TaskStub.getTask();
        var task2 = TaskStub.getTaskWithId(task1.getId());

        var actualEx = assertThrows(CodeableException.class, () -> ManageTasks.init(List.of(task1, task2)));

        assertTrue(actualEx.equalsByCode(ManageTaskError.doubledTaskErrorCode));
    }

    @Test
    void testInit_ValidManageTasksList_ReturnManageTasks() {
        var task1 = TaskStub.getTask();
        var task2 = TaskStub.getTask();
        var expectedTasks = List.of(task1, task2);

        var manageTasks = ManageTasks.init(expectedTasks);

        var actualManageTaskList = manageTasks.toList();
        assertEquals(2, actualManageTaskList.size());

        var actualTaskList = manageTasks.getTasks();
        assertEquals(2, actualTaskList.size());
        assertTrue(expectedTasks.containsAll(actualTaskList));

        var actualManageTasksMapById = actualManageTaskList.stream().
                collect(Collectors.toMap(ManageTask::getTaskId, Function.identity()));
        expectedTasks.forEach(expectedTask -> {
            var actualManageTask = actualManageTasksMapById.get(expectedTask.getId());
            assertEquals(expectedTask, actualManageTask.getTask());
            assertEquals(expectedTask.getId(), actualManageTask.getTaskId());
            assertNotNull(actualManageTask.getStartedAt());
            assertFalse(actualManageTask.getLastProcessedAt().isPresent());
        });
    }
}
