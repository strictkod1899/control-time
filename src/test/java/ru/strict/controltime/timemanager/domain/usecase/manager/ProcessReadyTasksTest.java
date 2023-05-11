package ru.strict.controltime.timemanager.domain.usecase.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProcessReadyTasksTest extends TimeManagerUseCaseTestCommon {

    @BeforeEach
    public void setupTest() {
        setupUseCase();
    }

    @Test
    void testProcess_GetTimeManagerRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(timeManagerRepositoryMock).getActiveManager();
        
        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.processReadyTasks());

        assertEquals(expectedEx, actualEx);
        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testProcess_TimeManagerNotFound_ThrowException() {
        doReturn(Optional.empty()).when(timeManagerRepositoryMock).getActiveManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.processReadyTasks());

        assertTrue(actualEx.equalsByCode(TimeManagerUseCaseError.activeTimeManagerNotFoundErrorCode));
        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testProcess_EmptyTasks_NoError() {
        var givenTimeManager = TimeManagerStub.getEmptyTimeManager();
        doReturn(Optional.of(givenTimeManager)).when(timeManagerRepositoryMock).getActiveManager();
        doNothing().when(timeManagerRepositoryMock).setActiveManager(any());
        doNothing().when(timeManagerPresenterMock).refreshTimeManager(any());

        timeManagerUseCase.processReadyTasks();

        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(any());
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
    }

    @Test
    void testProcess_WithoutReadyTasks_NoError() {
        var givenTimeManager = TimeManagerStub.getTimeManagerWithoutReadyTasks();
        doReturn(Optional.of(givenTimeManager)).when(timeManagerRepositoryMock).getActiveManager();
        doNothing().when(timeManagerRepositoryMock).setActiveManager(any());
        doNothing().when(timeManagerPresenterMock).refreshTimeManager(any());

        timeManagerUseCase.processReadyTasks();

        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(any());
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
    }

    @Test
    void testProcess_WithReadyTasks_NotificationPresenterError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(notificationPresenterMock).showMessage(any());

        var givenTimeManager = TimeManagerStub.getFullTimeManager();
        doReturn(Optional.of(givenTimeManager)).when(timeManagerRepositoryMock).getActiveManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.processReadyTasks());

        assertEquals(expectedEx, actualEx);
        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verify(notificationPresenterMock, only()).showMessage(any());
        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testProcess_WithReadyTasks_ValidProcess_NoError() {
        var expectedTimeManager = TimeManagerStub.getFullTimeManager();
        doReturn(Optional.of(expectedTimeManager)).when(timeManagerRepositoryMock).getActiveManager();
        doNothing().when(timeManagerRepositoryMock).setActiveManager(any());
        doNothing().when(timeManagerPresenterMock).refreshTimeManager(any());

        var expectedReadyTasks = expectedTimeManager.getReadyTaskIds();
        var expectedReadyTasksCount = expectedReadyTasks.size();
        expectedReadyTasks.forEach(readyTaskId -> {
            var expectedMessage = expectedTimeManager.getTaskMessage(readyTaskId).get();
            doNothing().when(notificationPresenterMock).showMessage(eq(expectedMessage));
        });

        timeManagerUseCase.processReadyTasks();

        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(eq(expectedTimeManager));
        verify(notificationPresenterMock, times(expectedReadyTasksCount)).showMessage(any());
        verifyNoInteractions(taskRepositoryMock);
    }
}
