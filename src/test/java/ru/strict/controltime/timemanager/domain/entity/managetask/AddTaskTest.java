package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class AddTaskTest {

    @Test
    void testAddTask_TaskIsNull_ThrowException() {
        var manageTasks = ManageTaskStub.getEmptyManageTasks();

        try {
            manageTasks.addTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_DoubledTask_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var existsManageTask = manageTasks.toList().iterator().next();
        var doubledNewTask = TaskStub.getTaskWithId(existsManageTask.getTaskId());

        try {
            manageTasks.addTask(doubledNewTask);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.doubledTaskErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_ValidTask_NoError() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var originalTasksSize = manageTasks.toList().size();

        var expectedNewTask = TaskStub.getTask();

        manageTasks.addTask(expectedNewTask);

        var actualTasksSize = manageTasks.toList().size();
        assertEquals(originalTasksSize+1, actualTasksSize);

        var actualManageTaskOptional = manageTasks.getManageTaskById(expectedNewTask.getId());
        assertTrue(actualManageTaskOptional.isPresent());

        var actualManageTask = actualManageTaskOptional.get();

        assertEquals(expectedNewTask, actualManageTask.getTask());
        assertEquals(expectedNewTask.getId(), actualManageTask.getTaskId());
        assertNotNull(actualManageTask.getStartedAt());
        assertFalse(actualManageTask.getLastProcessedAt().isPresent());
    }
}
