package ru.strict.controltime.domain.usecase.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.strict.controltime.boundary.model.CreateTaskParams;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;
import ru.strict.test.FailTestException;
import ru.strict.util.ReflectionUtil;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTaskTest extends TimeManagerUseCaseCommon {

    @BeforeEach
    public void setupTest() {
        setUpUseCase(List.of());
    }

    @Test
    void testAddTask_WithoutParams_ThrowException() {
        try {
            this.timeManagerUseCase.addTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TimeManagerUseCaseError.createTaskParamIsRequiredErrorCode));

            verify(taskRepositoryMock, only()).getAllTasks();
            verifyNoInteractions(notificationPresenterMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_WithoutMessage_ThrowException() {
        var createTaskParams = CreateTaskParams.builder().build();

        try {
            this.timeManagerUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.messageIsEmptyErrorCode));

            verify(taskRepositoryMock, only()).getAllTasks();
            verifyNoInteractions(notificationPresenterMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_WithoutSleepDuration_ThrowException() {
        var createTaskParams = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                build();

        try {
            this.timeManagerUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.durationIsRequiredErrorCode));

            verify(taskRepositoryMock, only()).getAllTasks();
            verifyNoInteractions(notificationPresenterMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_InsertTaskRepoError_ThrowException() {
        var expectedException = ExceptionStub.getException();
        doThrow(expectedException).when(taskRepositoryMock).insert(any());

        var createTaskParams = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofNanos(TaskStub.getSleepDuration().toNanos())).
                build();

        try {
            this.timeManagerUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertEquals(expectedException, ex);

            verifyNoInteractions(notificationPresenterMock);

            var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
            assertNotNull(timeManager);
            var isExistsNewTask = timeManager.getTasks().stream()
                    .anyMatch(task -> task.getMessage().toString().equals(createTaskParams.getMessage())
                                && task.getSleepDuration().toNanos() == createTaskParams.getSleepDuration().toNanos()
                    );
            assertFalse(isExistsNewTask);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_ValidParams_NoError() {
        doNothing().when(taskRepositoryMock).insert(any());

        var createTaskParams = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofNanos(TaskStub.getSleepDuration().toNanos())).
                build();

        this.timeManagerUseCase.addTask(createTaskParams);

        verifyNoInteractions(notificationPresenterMock);

        var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
        assertNotNull(timeManager);
        var isExistsNewTask = timeManager.getTasks().stream()
                .anyMatch(task -> task.getMessage().toString().equals(createTaskParams.getMessage())
                        && task.getSleepDuration().toNanos() == createTaskParams.getSleepDuration().toNanos()
                );
        assertTrue(isExistsNewTask);

        var insertedTaskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryMock).insert(insertedTaskCaptor.capture());
        var insertedTask = insertedTaskCaptor.getValue();
        assertEquals(createTaskParams.getMessage(), insertedTask.getMessage().toString());
        assertEquals(createTaskParams.getSleepDuration().toNanos(), insertedTask.getSleepDuration().toNanos());
    }
}