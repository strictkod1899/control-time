package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.boundary.repository.TaskRepository;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;
import ru.strict.test.MockitoUtil;
import ru.strict.util.ReflectionUtil;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TimeManagerUseCaseBuilderTest {

    TaskRepository taskRepositoryMock;
    NotificationPresenter notificationPresenterMock;

    @BeforeEach
    public void setUp() {
        taskRepositoryMock = mock(TaskRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        notificationPresenterMock = mock(NotificationPresenter.class, MockitoUtil.STRICT_BEHAVIOUR);
    }

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TimeManagerUseCaseError.taskRepositoryIsRequiredErrorCode,
                TimeManagerUseCaseError.notificationPresenterIsRequiredErrorCode
        );

        try {
            TimeManagerUseCaseImpl.builder().build();
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testBuild_AllValidParams_EmptyTasks_ReturnUseCase() {
        doReturn(List.of()).when(taskRepositoryMock).getAllTasks();

        var timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                taskRepository(taskRepositoryMock).
                notificationPresenter(notificationPresenterMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);

        var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
        assertNotNull(timeManager);
        assertEquals(List.of(), timeManager.getTasks());
    }

    @Test
    void testBuild_AllValidParams_NotEmptyTasks_ReturnUseCase() {
        var expectedTasks = List.of(
                TaskStub.getTask(),
                TaskStub.getTask()
        );
        doReturn(expectedTasks).when(taskRepositoryMock).getAllTasks();

        var timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                taskRepository(taskRepositoryMock).
                notificationPresenter(notificationPresenterMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);

        var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
        assertNotNull(timeManager);
        assertEquals(expectedTasks.size(), timeManager.getTasks().size());
        assertTrue(expectedTasks.containsAll(timeManager.getTasks()));
    }
}
