package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();

        try {
            manageTasks.deleteTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var originalTasksCount = manageTasks.toList().size();
        var taskId = TaskStub.getId();

        try {
            manageTasks.deleteTask(taskId);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskNotFoundErrorCode));

            var actualTasksCount = manageTasks.toList().size();
            assertEquals(originalTasksCount, actualTasksCount);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_ValidParams_NoError() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var manageTaskForRemove = manageTasks.toList().iterator().next();
        var removeTaskId = manageTaskForRemove.getTaskId();

        manageTasks.deleteTask(removeTaskId);

        assertFalse(manageTasks.getManageTaskById(removeTaskId).isPresent());
    }
}
