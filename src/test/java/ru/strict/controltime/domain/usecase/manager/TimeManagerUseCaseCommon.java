package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.boundary.repository.TaskRepository;
import ru.strict.controltime.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.test.MockitoUtil;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
class TimeManagerUseCaseCommon {
    TaskRepository taskRepositoryMock;
    NotificationPresenter notificationPresenterMock;
    TimeManagerUseCase timeManagerUseCase;

    void setUpUseCase(List<Task> tasks) {
        taskRepositoryMock = mock(TaskRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        notificationPresenterMock = mock(NotificationPresenter.class, MockitoUtil.STRICT_BEHAVIOUR);

        doReturn(tasks).when(taskRepositoryMock).getAllTasks();

        timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                taskRepository(taskRepositoryMock).
                notificationPresenter(notificationPresenterMock).
                build();
    }
}
