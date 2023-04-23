package ru.strict.controltime.task.domain.usecase.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskUseCaseBuilderTest {

    @Test
    void testBuild_WithoutParams_ThrowException() {
        var expectedErrorCodes = List.of(
                TaskUseCaseError.taskRepositoryIsRequiredErrorCode,
                TaskUseCaseError.taskEventPublisherIsRequiredErrorCode
        );

        try {
            TaskUseCaseImpl.builder().build();
        } catch (Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testBuild_AllValidParams_ReturnUseCase() {
        var taskRepositoryMock = mock(TaskRepository.class);
        var taskEventPublisherMock = mock(TaskEventPublisher.class);

        var timeManagerUseCase = TaskUseCaseImpl.builder().
                taskRepository(taskRepositoryMock).
                taskEventPublisher(taskEventPublisherMock).
                build();

        AssertUtil.assertFieldsNotNull(timeManagerUseCase);
    }
}
