package ru.strict.controltime.task.domain.usecase.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.boundary.usecase.TaskUseCase;
import ru.strict.test.MockitoUtil;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
class TaskUseCaseTestCommon {
    TaskRepository taskRepositoryMock;
    TaskEventPublisher taskEventPublisherMock;
    TaskUseCase taskUseCase;

    void setupUseCase() {
        taskRepositoryMock = mock(TaskRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        taskEventPublisherMock = mock(TaskEventPublisher.class, MockitoUtil.STRICT_BEHAVIOUR);

        taskUseCase = new TaskUseCaseImpl(taskRepositoryMock, taskEventPublisherMock);
    }
}
