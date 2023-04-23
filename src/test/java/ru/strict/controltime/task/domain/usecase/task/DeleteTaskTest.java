package ru.strict.controltime.task.domain.usecase.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;

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
        var actualEx = assertThrows(CodeableException.class, () -> this.taskUseCase.deleteTask(null));

        assertTrue(actualEx.equalsByCode(TaskUseCaseError.taskIdIsRequiredErrorCode));

        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(taskEventPublisherMock);
    }

    @Test
    void testDeleteTask_EmptyTaskId_ThrowException() {
        var actualEx = assertThrows(CodeableException.class, () -> this.taskUseCase.deleteTask(""));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));

        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(taskEventPublisherMock);
    }

    @Test
    void testDeleteTask_TaskNotFound_ThrowException() {
        doReturn(Optional.empty()).when(taskRepositoryMock).getById(any());

        var givenTaskId = TaskStub.getId();

        var actualEx = assertThrows(CodeableException.class, () -> this.taskUseCase.deleteTask(givenTaskId.toString()));

        assertTrue(actualEx.equalsByCode(TaskUseCaseError.taskNotFoundErrorCode));

        verify(taskRepositoryMock, only()).getById(any());
        verifyNoInteractions(taskEventPublisherMock);
    }

    @Test
    void testDeleteTask_DeleteTaskRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(taskRepositoryMock).delete(any());

        var givenTask = TaskStub.getTask();
        doReturn(Optional.of(givenTask)).when(taskRepositoryMock).getById(any());

        var actualEx = assertThrows(CodeableException.class, () -> this.taskUseCase.deleteTask(givenTask.getId().toString()));

        assertEquals(expectedEx, actualEx);

        verify(taskRepositoryMock).getById(any());
        verify(taskRepositoryMock).delete(any());
        verifyNoInteractions(taskEventPublisherMock);
    }

    @Test
    void testDeleteTask_PublishTaskEventError_NoError() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(taskEventPublisherMock).taskDeleted(any());

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
