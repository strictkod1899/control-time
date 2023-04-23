package ru.strict.controltime.timemanager.domain.entity.managetask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.controltime.timemanager.testdouble.stub.entity.ManageTaskStub;
import ru.strict.exception.CodeableException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class ManageTasksFromTest {

    @Test
    void testFrom_ManageTasksListIsNull_ThrowException() {
        var actualEx = assertThrows(CodeableException.class, () -> ManageTasks.from(null));

        assertTrue(actualEx.equalsByCode(ManageTaskError.manageTaskListIsRequiredErrorCode));
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

        var actualEx = assertThrows(CodeableException.class, () -> ManageTasks.from(List.of(manageTask1, manageTask2)));

        assertTrue(actualEx.equalsByCode(ManageTaskError.doubledTaskErrorCode));
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
