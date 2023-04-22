package ru.strict.controltime.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.testdouble.stub.entity.ManageTaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class ManageTasksFromTest {

    @Test
    void testFrom_ManageTasksListIsNull_ThrowException() {
        try {
            ManageTasks.from(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.manageTaskListIsRequiredErrorCode));

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_ManageTasksListIsEmpty_ReturnManageTasks() {
        var manageTasks = ManageTasks.from(List.of());
        assertTrue(manageTasks.toList().isEmpty());
    }

    @Test
    void testFrom_DoubledManageTasks_ThrowException() {
        var manageTask1 = ManageTaskStub.getBaseManageTask();
        var manageTask2 = ManageTaskStub.getFullManageTaskBuilder().
                task(manageTask1.getTask()).
                build();

        try {
            ManageTasks.from(List.of(manageTask1, manageTask2));
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(ManageTaskError.doubledTaskErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_ValidManageTasksList_ReturnManageTasks() {
        var manageTask1 = ManageTaskStub.getBaseManageTask();
        var manageTask2 = ManageTaskStub.getFullManageTask();
        var expectedManageTasks = List.of(manageTask1, manageTask2);

        var manageTasks = ManageTasks.from(expectedManageTasks);

        var actualManageTasks = manageTasks.toList();
        assertEquals(2, actualManageTasks.size());

        var actualManageTasksMapById = actualManageTasks.stream().
                collect(Collectors.toMap(ManageTask::getTaskId, Function.identity()));
        expectedManageTasks.forEach(expectedManageTask -> {
            var actualManageTask = actualManageTasksMapById.get(expectedManageTask.getTaskId());
            assertEquals(expectedManageTask, actualManageTask);
        });
    }
}
