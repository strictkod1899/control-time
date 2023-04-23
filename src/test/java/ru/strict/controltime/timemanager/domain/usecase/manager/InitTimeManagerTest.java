package ru.strict.controltime.timemanager.domain.usecase.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InitTimeManagerTest extends TimeManagerUseCaseCommon {

    @BeforeEach
    public void setupTest() {
        setUpUseCase();
    }

    @Test
    void testInit_GetTimeManagerRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(timeManagerRepositoryMock).getActiveManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.initTimeManager());

        assertEquals(expectedEx, actualEx);

        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
    }

    @Test
    void testInit_TimeManagerExists_NoError() {
        var givenTimeManager = TimeManagerStub.getFullTimeManager();
        doReturn(Optional.of(givenTimeManager)).when(timeManagerRepositoryMock).getActiveManager();

        timeManagerUseCase.initTimeManager();

        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(notificationPresenterMock);
        verifyNoInteractions(taskRepositoryMock);
    }

    @Test
    void testInit_TimeManagerNotExists_GetTasksRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(taskRepositoryMock).getAllTasks();
        doReturn(Optional.empty()).when(timeManagerRepositoryMock).getActiveManager();

        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.initTimeManager());

        assertEquals(expectedEx, actualEx);

        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verify(taskRepositoryMock, only()).getAllTasks();
        verifyNoInteractions(notificationPresenterMock);
    }

    @Test
    void testInit_TimeManagerNotExists_SetActiveManagerRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(timeManagerRepositoryMock).setActiveManager(any());
        doReturn(Optional.empty()).when(timeManagerRepositoryMock).getActiveManager();
        doReturn(TaskStub.getTasks()).when(taskRepositoryMock).getAllTasks();

        var actualEx = assertThrows(CodeableException.class, () -> timeManagerUseCase.initTimeManager());

        assertEquals(expectedEx, actualEx);

        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(any());
        verify(taskRepositoryMock, only()).getAllTasks();
        verifyNoInteractions(notificationPresenterMock);
    }

    @Test
    void testInit_TimeManagerNotExists_ValidProcess_NoError() {
        var expectedTasks = TaskStub.getTasks();
        doReturn(Optional.empty()).when(timeManagerRepositoryMock).getActiveManager();
        doReturn(expectedTasks).when(taskRepositoryMock).getAllTasks();
        doNothing().when(timeManagerRepositoryMock).setActiveManager(any());

        timeManagerUseCase.initTimeManager();

        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(any());
        verify(taskRepositoryMock, only()).getAllTasks();
        verifyNoInteractions(notificationPresenterMock);

        var setActiveTimeManagerCaptor = ArgumentCaptor.forClass(TimeManager.class);
        verify(timeManagerRepositoryMock).setActiveManager(setActiveTimeManagerCaptor.capture());
        var newActiveTimeManager = setActiveTimeManagerCaptor.getValue();

        var actualTasks = newActiveTimeManager.getTasks();
        assertEquals(expectedTasks.size(), actualTasks.size());
        assertTrue(expectedTasks.containsAll(actualTasks));
        assertTrue(newActiveTimeManager.getReadyTaskIds().isEmpty());
    }
}
