package ru.strict.controltime.timemanager.domain.usecase.task.event.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.exception.ErrorsException;
import ru.strict.test.AssertUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AddTaskHandlerBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskHandlerError.timeManagerPresenterIsRequiredErrorCode,
                TaskHandlerError.timeManagerRepositoryIsRequiredErrorCode,
                TaskHandlerError.taskRepositoryIsRequiredErrorCode
        );

        var actualEx = assertThrows(ErrorsException.class, () -> AddTaskHandler.builder().build());

        AssertUtil.assertExceptionByCodes(actualEx, expectedErrorCodes);
    }

    @Test
    void testBuild_AllValidParams_ReturnUseCase() {
        var timeManagerPresenterMock = mock(TimeManagerPresenter.class);
        var timeManagerRepositoryMock = mock(TimeManagerRepository.class);
        var taskRepositoryMock = mock(TaskRepository.class);

        var timeManagerUseCase = AddTaskHandler.builder().
                timeManagerPresenter(timeManagerPresenterMock).
                timeManagerRepository(timeManagerRepositoryMock).
                taskRepository(taskRepositoryMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);
    }
}
