package ru.strict.controltime.initservice;

import ru.strict.controltime.common.task.boundary.usecase.TaskEventUseCase;
import ru.strict.controltime.timemanager.adapter.event.task.TaskEventListener;
import ru.strict.event.EventBroker;
import ru.strict.ioc.annotation.Component;

public class EventListenersInit {

    @Component
    public TaskEventListener taskEventListener(
            @Component("taskEventBus") EventBroker taskEventBroker,
            TaskEventUseCase taskEventUseCase,
            @Component("taskEventTopic") String topic
            ) {
        var taskEventListener = TaskEventListener.init(taskEventUseCase);
        taskEventBroker.subscribe(topic, taskEventListener);

        return taskEventListener;
    }
}
