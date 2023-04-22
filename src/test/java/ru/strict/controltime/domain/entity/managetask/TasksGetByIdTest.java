package ru.strict.controltime.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.ManageTaskStub;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TasksGetByIdTest {

    @Test
    void testGetById_TaskIdIsNull_ThrowException() {
        var manageTasks = ManageTaskStub.getFullManageTasks();

        try {
            manageTasks.getManageTaskById(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.taskIdIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
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
