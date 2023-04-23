package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TasksGetByIdTest {

    @Test
    void testGetById_TaskIdIsNull_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();

        var actualEx = assertThrows(CodeableException.class, () -> manageTasks.getManageTaskById(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
    }

    @Test
    void testGetById_TaskNotFound_ReturnNull() {
        var manageTasks = ManageTaskStub.getFullManageTasks();

        var manageTask = manageTasks.getManageTaskById(TaskStub.getId());

        assertFalse(manageTask.isPresent());
    }

    @Test
    void testGetById_ValidTaskId_ReturnTask() {
        var manageTasks = ManageTaskStub.getFullManageTasks();
        var expectedManageTask = manageTasks.toList().iterator().next();

        var manageTask = manageTasks.getManageTaskById(expectedManageTask.getTaskId());

        assertTrue(manageTask.isPresent());
        assertEquals(expectedManageTask, manageTask.get());
    }
}
