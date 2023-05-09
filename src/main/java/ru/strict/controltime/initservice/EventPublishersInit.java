package ru.strict.controltime.initservice;

import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.task.adapter.event.task.TaskEventPublisherImpl;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.event.EventBroker;
import ru.strict.ioc.annotation.Component;

public class EventPublishersInit {

    @Component
    public TaskEventPublisher taskEventPublisher(
            @Component("taskEventBus") EventBroker<TaskEvent> eventBroker
    ) {
        return TaskEventPublisherImpl.from(eventBroker);
    }
}
