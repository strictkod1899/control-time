package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class DeleteTaskTest {

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();

        var actualEx = assertThrows(CodeableException.class, () -> manageTasks.deleteTask(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var originalTasksCount = manageTasks.toList().size();
        var taskId = TaskStub.getId();

        var actualEx = assertThrows(CodeableException.class, () -> manageTasks.deleteTask(taskId));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskNotFoundErrorCode));

        var actualTasksCount = manageTasks.toList().size();
        assertEquals(originalTasksCount, actualTasksCount);
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
