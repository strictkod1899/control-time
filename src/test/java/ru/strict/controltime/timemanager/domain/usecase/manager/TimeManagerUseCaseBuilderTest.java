package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.ErrorsException;
import ru.strict.test.AssertUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TimeManagerUseCaseBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TimeManagerUseCaseError.notificationPresenterIsRequiredErrorCode,
                TimeManagerUseCaseError.timeManagerRepositoryIsRequiredErrorCode,
                TimeManagerUseCaseError.taskRepositoryIsRequiredErrorCode
        );

        var actualEx = assertThrows(ErrorsException.class, () -> TimeManagerUseCaseImpl.builder().build());

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
    }

    @Test
    void testBuild_AllValidParams_ReturnUseCase() {
        var notificationPresenterMock = mock(NotificationPresenter.class);
        var timeManagerRepositoryMock = mock(TimeManagerRepository.class);
        var taskRepositoryMock = mock(TaskRepository.class);

        var timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                notificationPresenter(notificationPresenterMock).
                timeManagerRepository(timeManagerRepositoryMock).
                taskRepository(taskRepositoryMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);
    }
}
