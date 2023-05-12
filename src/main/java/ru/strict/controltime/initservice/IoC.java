package ru.strict.controltime.initservice;

import ru.strict.controltime.Main;
import ru.strict.controltime.common.task.domain.usecase.event.TaskEventUseCaseImpl;
import ru.strict.controltime.task.adapter.event.task.TaskEventPublisherImpl;
import ru.strict.controltime.task.adapter.repository.task.TaskJsonRepository;
import ru.strict.controltime.task.domain.usecase.task.TaskUseCaseImpl;
import ru.strict.controltime.timemanager.adapter.controller.inner.manager.TimeManagerInnerController;
import ru.strict.controltime.timemanager.adapter.presenter.notification.NotificationPresenterImpl;
import ru.strict.controltime.timemanager.adapter.presenter.timemanager.TimeManagerPresenterImpl;
import ru.strict.controltime.timemanager.adapter.repository.manager.TimeManagerInMemoryRepository;
import ru.strict.controltime.timemanager.adapter.scheduler.manager.ProcessTasksScheduler;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.domain.usecase.manager.TimeManagerUseCaseImpl;
import ru.strict.controltime.timemanager.domain.usecase.task.event.handler.AddTaskHandler;
import ru.strict.controltime.view.manager.TimeManagerViewController;
import ru.strict.controltime.view.manager.scheduler.ComputerWorkDurationScheduler;
import ru.strict.controltime.view.manager.scheduler.RefreshTimeManagerScheduler;
import ru.strict.controltime.view.notification.NotificationViewController;
import ru.strict.event.EventBus;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.ioc.InstanceType;
import ru.strict.ioc.SingletonIoC;
import ru.strict.util.ClassUtil;

public class IoC extends SingletonIoC {

    public static IoC instance() {
        if (getInstance() == null || !(getInstance() instanceof IoC)) {
            setInstance(new IoC());
        }
        return getInstance();
    }

    @Override
    protected void configure() {
        addSingleton("appPath", String.class, () -> ClassUtil.getPathByClass(Main.class));
        addComponent(JacksonObjectMapper.class);
        addComponent("taskEventBus", EventBus.class);

        addComponent(EnvRegistryInit.class, InstanceType.CONFIGURATION);
        addComponent(RepositoriesInit.class, InstanceType.CONFIGURATION);
        addComponent(UseCasesInit.class, InstanceType.CONFIGURATION);
        addComponent(EventListenersInit.class, InstanceType.CONFIGURATION);

        addComponent(TaskJsonRepository.class);
        addComponent(TimeManagerInMemoryRepository.class);
        addComponent(TimeManagerPresenterImpl.class);
        addComponent(NotificationPresenterImpl.class);
        addComponent(TaskEventPublisherImpl.class);
        addComponent(TaskUseCaseImpl.class);
        addComponent(TimeManagerUseCaseImpl.class);
        addComponent(TaskEventUseCaseImpl.class);
        addComponent(AddTaskHandler.class);
        addComponent(TimeManagerInnerController.class);
        addComponent(TimeManagerViewController.class);
        addComponent(NotificationViewController.class);
        addComponent(ComputerWorkDurationScheduler.class);
        addComponent(RefreshTimeManagerScheduler.class);
        addComponent(ProcessTasksScheduler.class);

        addComponent(RepositoriesInit.Start.class, InstanceType.CONFIGURATION);
        addComponent(ViewInit.Start.class, InstanceType.CONFIGURATION);
        addComponent(SchedulerInit.Start.class, InstanceType.CONFIGURATION);
    }
}
