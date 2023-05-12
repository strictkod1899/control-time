package ru.strict.controltime.timemanager.domain.usecase.task.event.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;
import ru.strict.test.ExceptionStub;
import ru.strict.test.MockitoUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AddTaskHandlerTest {
    TimeManagerRepository timeManagerRepositoryMock;
    TaskRepository taskRepositoryMock;
    TimeManagerPresenter timeManagerPresenterMock;

    AddTaskHandler addTaskHandler;

    @BeforeEach
    public void setupTest() {
        timeManagerRepositoryMock = mock(TimeManagerRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        taskRepositoryMock = mock(TaskRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        timeManagerPresenterMock = mock(TimeManagerPresenter.class, MockitoUtil.STRICT_BEHAVIOUR);

        addTaskHandler = new AddTaskHandler(taskRepositoryMock,
                timeManagerRepositoryMock,
                timeManagerPresenterMock);
    }

    @Test
    void testHandle_WithoutTaskId_ThrowException() {
        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                build();

        var actualEx = assertThrows(CodeableException.class, () -> addTaskHandler.handle(givenEvent));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));
        verifyNoInteractions(taskRepositoryMock);
        verifyNoInteractions(timeManagerRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testHandle_TaskRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(taskRepositoryMock).getById(any());

        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                taskId(TaskStub.getId().toString()).
                build();

        var actualEx = assertThrows(CodeableException.class, () -> addTaskHandler.handle(givenEvent));

        assertEquals(expectedEx, actualEx);
        verify(taskRepositoryMock, only()).getById(any());
        verifyNoInteractions(timeManagerRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testHandle_TaskNotFound_ThrowException() {
        doReturn(Optional.empty()).when(taskRepositoryMock).getById(any());

        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                taskId(TaskStub.getId().toString()).
                build();

        var actualEx = assertThrows(CodeableException.class, () -> addTaskHandler.handle(givenEvent));

        assertTrue(actualEx.equalsByCode(TaskHandlerError.taskNotFoundErrorCode));
        verify(taskRepositoryMock, only()).getById(any());
        verifyNoInteractions(timeManagerRepositoryMock);
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testHandle_TimeManagerRepoError_ThrowException() {
        var expectedEx = ExceptionStub.getException();
        doThrow(expectedEx).when(timeManagerRepositoryMock).getActiveManager();

        var givenTask = TaskStub.getTask();
        doReturn(Optional.ofNullable(givenTask)).when(taskRepositoryMock).getById(any());

        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                taskId(givenTask.getId().toString()).
                build();

        var actualEx = assertThrows(CodeableException.class, () -> addTaskHandler.handle(givenEvent));

        assertEquals(expectedEx, actualEx);
        verify(taskRepositoryMock, only()).getById(any());
        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testHandle_TimeManagerNotFound_ThrowException() {
        var givenTask = TaskStub.getTask();
        doReturn(Optional.ofNullable(givenTask)).when(taskRepositoryMock).getById(any());
        doReturn(Optional.empty()).when(timeManagerRepositoryMock).getActiveManager();

        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                taskId(givenTask.getId().toString()).
                build();

        var actualEx = assertThrows(CodeableException.class, () -> addTaskHandler.handle(givenEvent));

        assertTrue(actualEx.equalsByCode(TaskHandlerError.activeTimeManagerNotFoundErrorCode));
        verify(taskRepositoryMock, only()).getById(any());
        verify(timeManagerRepositoryMock, only()).getActiveManager();
        verifyNoInteractions(timeManagerPresenterMock);
    }

    @Test
    void testHandle_ValidParams_NoError() {
        var givenTask = TaskStub.getTask();
        var givenTimeManager = TimeManagerStub.getFullTimeManager();

        doReturn(Optional.ofNullable(givenTask)).when(taskRepositoryMock).getById(any());
        doReturn(Optional.ofNullable(givenTimeManager)).when(timeManagerRepositoryMock).getActiveManager();
        doNothing().when(timeManagerRepositoryMock).setActiveManager(eq(givenTimeManager));
        doNothing().when(timeManagerPresenterMock).refreshTimeManager(any());

        var givenEvent = TaskEvent.builder().
                action(TaskEventAction.CREATED.getAction()).
                taskId(givenTask.getId().toString()).
                build();

        addTaskHandler.handle(givenEvent);

        verify(taskRepositoryMock, only()).getById(any());
        verify(timeManagerRepositoryMock).getActiveManager();
        verify(timeManagerRepositoryMock).setActiveManager(eq(givenTimeManager));
        verify(timeManagerPresenterMock).refreshTimeManager(any());
    }
}
