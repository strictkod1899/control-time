package ru.strict.controltime.timemanager.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.boundary.usecase.TimeManagerUseCase;
import ru.strict.test.MockitoUtil;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PACKAGE)
class TimeManagerUseCaseCommon {
    TimeManagerRepository timeManagerRepositoryMock;
    NotificationPresenter notificationPresenterMock;
    TaskRepository taskRepositoryMock;
    TimeManagerUseCase timeManagerUseCase;

    void setUpUseCase() {
        timeManagerRepositoryMock = mock(TimeManagerRepository.class, MockitoUtil.STRICT_BEHAVIOUR);
        notificationPresenterMock = mock(NotificationPresenter.class, MockitoUtil.STRICT_BEHAVIOUR);
        taskRepositoryMock = mock(TaskRepository.class, MockitoUtil.STRICT_BEHAVIOUR);

        timeManagerUseCase = TimeManagerUseCaseImpl.builder().
                timeManagerRepository(timeManagerRepositoryMock).
                notificationPresenter(notificationPresenterMock).
                taskRepository(taskRepositoryMock).
                build();
    }
}
