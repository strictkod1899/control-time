package ru.strict.controltime.task.domain.usecase.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.strict.controltime.task.boundary.model.CreateTaskData;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskError;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;
import ru.strict.test.FailTestException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddTaskTest extends TaskUseCaseCommon {

    @BeforeEach
    public void setupTest() {
        setUpUseCase();
    }

    @Test
    void testAddTask_WithoutParams_ThrowException() {
        try {
            this.taskUseCase.addTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskUseCaseError.createTaskDataIsRequiredErrorCode));

            verifyNoInteractions(taskRepositoryMock);
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_WithoutMessage_ThrowException() {
        var createTaskParams = CreateTaskData.builder().build();

        try {
            this.taskUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.messageIsEmptyErrorCode));

            verifyNoInteractions(taskRepositoryMock);
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_WithoutSleepDuration_ThrowException() {
        var createTaskParams = CreateTaskData.builder().
                message(TaskStub.getMessage().toString()).
                build();

        try {
            this.taskUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.durationIsRequiredErrorCode));

            verifyNoInteractions(taskRepositoryMock);
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_InsertTaskRepoError_ThrowException() {
        var expectedException = ExceptionStub.getException();
        doThrow(expectedException).when(taskRepositoryMock).insert(any());

        var createTaskParams = CreateTaskData.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofNanos(TaskStub.getSleepDuration().toNanos())).
                build();

        try {
            this.taskUseCase.addTask(createTaskParams);
        } catch (CodeableException ex) {
            assertEquals(expectedException, ex);

            verify(taskRepositoryMock, only()).insert(any());
            verifyNoInteractions(taskEventPublisherMock);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testAddTask_PublishTaskEventError_NoError() {
        var expectedException = ExceptionStub.getException();
        doThrow(expectedException).when(taskEventPublisherMock).taskCreated(any());
        doNothing().when(taskRepositoryMock).insert(any());

        var createTaskParams = CreateTaskData.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofNanos(TaskStub.getSleepDuration().toNanos())).
                build();

        this.taskUseCase.addTask(createTaskParams);

        verify(taskRepositoryMock, only()).insert(any());
        verify(taskEventPublisherMock, only()).taskCreated(any());

        var insertedTaskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryMock).insert(insertedTaskCaptor.capture());
        var insertedTask = insertedTaskCaptor.getValue();
        assertEquals(createTaskParams.getMessage(), insertedTask.getMessage().toString());
        assertEquals(createTaskParams.getSleepDuration().toNanos(), insertedTask.getSleepDuration().toNanos());
    }

    @Test
    void testAddTask_ValidParams_NoError() {
        doNothing().when(taskEventPublisherMock).taskCreated(any());
        doNothing().when(taskRepositoryMock).insert(any());

        var createTaskParams = CreateTaskData.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofNanos(TaskStub.getSleepDuration().toNanos())).
                build();

        this.taskUseCase.addTask(createTaskParams);

        verify(taskRepositoryMock, only()).insert(any());
        verify(taskEventPublisherMock, only()).taskCreated(any());

        var insertedTaskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepositoryMock).insert(insertedTaskCaptor.capture());
        var insertedTask = insertedTaskCaptor.getValue();
        assertEquals(createTaskParams.getMessage(), insertedTask.getMessage().toString());
        assertEquals(createTaskParams.getSleepDuration().toNanos(), insertedTask.getSleepDuration().toNanos());
    }
}
