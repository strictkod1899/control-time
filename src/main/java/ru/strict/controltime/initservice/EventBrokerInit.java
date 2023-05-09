package ru.strict.controltime.initservice;

import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.event.EventBroker;
import ru.strict.event.EventBus;
import ru.strict.ioc.annotation.Component;

public class EventBrokerInit {

    @Component
    public EventBroker<TaskEvent> taskEventBus() {
        return new EventBus<>();
    }
}
