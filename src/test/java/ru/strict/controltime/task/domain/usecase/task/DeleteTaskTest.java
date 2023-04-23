package ru.strict.controltime.task.domain.usecase.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;
import ru.strict.test.FailTestException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteTaskTest extends TaskUseCaseCommon {

    @BeforeEach
    public void setupTest() {
        setUpUseCase();
    }

    @Test
    void testDeleteTask_TaskIdIsNull_ThrowException() {
        try {
            this.taskUseCase.deleteTask(null);
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskUseCaseError.taskIdIsRequiredErrorCode));

            verifyNoInteractions(taskRepositoryMock);
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_EmptyTaskId_ThrowException() {
        try {
            this.taskUseCase.deleteTask("");
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(EntityIdError.idIsEmptyErrorCode));

            verifyNoInteractions(taskRepositoryMock);
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        doReturn(Optional.empty()).when(taskRepositoryMock).getById(any());

        var givenTaskId = TaskStub.getId();

        try {
            this.taskUseCase.deleteTask(givenTaskId.toString());
        } catch (CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskUseCaseError.taskNotFoundErrorCode));

            verify(taskRepositoryMock, only()).getById(any());
            verifyNoInteractions(taskEventPublisherMock);

            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_DeleteTaskRepoError_ThrowException() {
        var expectedException = ExceptionStub.getException();
        doThrow(expectedException).when(taskRepositoryMock).delete(any());

        var givenTask = TaskStub.getTask();
        doReturn(Optional.of(givenTask)).when(taskRepositoryMock).getById(any());

        try {
            this.taskUseCase.deleteTask(givenTask.getId().toString());
        } catch (CodeableException ex) {
            assertEquals(expectedException, ex);

            verify(taskRepositoryMock).getById(any());
            verify(taskRepositoryMock).delete(any());
            verifyNoInteractions(taskEventPublisherMock);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testDeleteTask_PublishTaskEventError_NoError() {
        var expectedException = ExceptionStub.getException();
        doThrow(expectedException).when(taskEventPublisherMock).taskDeleted(any());

        var givenTask = TaskStub.getTask();
        doReturn(Optional.of(givenTask)).when(taskRepositoryMock).getById(any());
        doNothing().when(taskRepositoryMock).delete(any());

        this.taskUseCase.deleteTask(givenTask.getId().toString());

        verify(taskRepositoryMock).getById(any());
        verify(taskRepositoryMock).delete(any());
        verify(taskEventPublisherMock, only()).taskDeleted(any());

        var deletedTaskIdCaptor = ArgumentCaptor.forClass(TaskId.class);
        verify(taskRepositoryMock).delete(deletedTaskIdCaptor.capture());
        var deletedTaskId = deletedTaskIdCaptor.getValue();
        assertEquals(givenTask.getId(), deletedTaskId);
    }

    @Test
    void testDeleteTask_ValidParams_NoError() {
        var givenTask = TaskStub.getTask();
        doReturn(Optional.of(givenTask)).when(taskRepositoryMock).getById(any());
        doNothing().when(taskRepositoryMock).delete(any());

        doNothing().when(taskEventPublisherMock).taskDeleted(any());

        this.taskUseCase.deleteTask(givenTask.getId().toString());

        verify(taskRepositoryMock).getById(any());
        verify(taskRepositoryMock).delete(any());
        verify(taskEventPublisherMock, only()).taskDeleted(any());

        var deletedTaskIdCaptor = ArgumentCaptor.forClass(TaskId.class);
        verify(taskRepositoryMock).delete(deletedTaskIdCaptor.capture());
        var deletedTaskId = deletedTaskIdCaptor.getValue();
        assertEquals(givenTask.getId(), deletedTaskId);
    }
}
