package ru.strict.controltime.initservice;

import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.controltime.common.task.domain.usecase.event.TaskEventUseCaseImpl;
import ru.strict.controltime.common.task.domain.usecase.event.handler.TaskEventHandler;
import ru.strict.controltime.common.task.domain.usecase.event.handler.TaskEventHandlerProvider;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.domain.usecase.task.TaskUseCaseImpl;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;
import ru.strict.controltime.timemanager.domain.usecase.manager.TimeManagerUseCaseImpl;
import ru.strict.controltime.timemanager.domain.usecase.task.event.handler.AddTaskHandler;
import ru.strict.ioc.annotation.Component;

public class UseCasesInit {

    @Component
    public TaskUseCaseImpl taskUseCase(
            TaskRepository taskRepository,
            TaskEventPublisher taskEventPublisher
    ) {
        return TaskUseCaseImpl.builder().
                taskRepository(taskRepository).
                taskEventPublisher(taskEventPublisher).
                build();
    }

    @Component
    public TimeManagerUseCaseImpl timeManagerUseCase(
            ru.strict.controltime.timemanager.boundary.repository.TaskRepository taskRepository,
            TimeManagerRepository timeManagerRepository,
            NotificationPresenter notificationPresenter,
            TimeManagerPresenter timeManagerPresenter) {
        return TimeManagerUseCaseImpl.builder().
                taskRepository(taskRepository).
                timeManagerRepository(timeManagerRepository).
                notificationPresenter(notificationPresenter).
                timeManagerPresenter(timeManagerPresenter).
                build();
    }

    @Component
    public TaskEventHandlerProvider taskEventHandlerProvider(
            AddTaskHandler addTaskHandler
    ) {
        var taskEventHandlerProvider = TaskEventHandlerProvider.init();
        taskEventHandlerProvider.putHandlers(TaskEventAction.CREATED, addTaskHandler);
        taskEventHandlerProvider.putSkippedAction(TaskEventAction.DELETED);

        return taskEventHandlerProvider;
    }

    @Component
    public AddTaskHandler addTaskEventHandler(
            ru.strict.controltime.timemanager.boundary.repository.TaskRepository taskRepository,
            TimeManagerRepository timeManagerRepository,
            TimeManagerPresenter timeManagerPresenter
    ) {
        return AddTaskHandler.builder().
                taskRepository(taskRepository).
                timeManagerRepository(timeManagerRepository).
                timeManagerPresenter(timeManagerPresenter).
                build();
    }

    @Component
    public TaskEventUseCaseImpl taskEventUseCase(
            TaskEventHandlerProvider taskEventHandlerProvider
    ) {
        return TaskEventUseCaseImpl.init(taskEventHandlerProvider);
    }
}
