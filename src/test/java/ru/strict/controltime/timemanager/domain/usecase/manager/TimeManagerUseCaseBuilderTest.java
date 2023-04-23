package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TimeManagerUseCaseBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TimeManagerUseCaseError.notificationPresenterIsRequiredErrorCode,
                TimeManagerUseCaseError.timeManagerRepositoryIsRequiredErrorCode
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
    void testBuild_AllValidParams_ReturnUseCase() {
        var notificationPresenterMock = mock(NotificationPresenter.class);
        var timeManagerRepositoryMock = mock(TimeManagerRepository.class);

        var timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                notificationPresenter(notificationPresenterMock).
                timeManagerRepository(timeManagerRepositoryMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);
    }
}
