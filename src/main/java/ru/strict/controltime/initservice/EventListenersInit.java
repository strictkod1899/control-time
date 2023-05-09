package ru.strict.controltime.initservice;

import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.common.task.boundary.usecase.TaskEventUseCase;
import ru.strict.controltime.timemanager.adapter.event.task.TaskEventListener;
import ru.strict.event.EventBroker;
import ru.strict.ioc.annotation.Component;

public class EventListenersInit {

    @Component
    public TaskEventListener taskEventListener(
            @Component("taskEventBus") EventBroker<TaskEvent> taskEventBroker,
            TaskEventUseCase taskEventUseCase
    ) {
        var taskEventListener = TaskEventListener.init(taskEventUseCase);
        taskEventBroker.subscribe(taskEventListener);

        return taskEventListener;
    }
}
