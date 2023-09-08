package ru.strict.controltime.task.adapter.event.task;

import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.event.EventBroker;
import ru.strict.event.EventPublisher;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.ioc.annotation.Component;

public class TaskEventPublisherImpl extends EventPublisher implements TaskEventPublisher {

    public TaskEventPublisherImpl(
            @Component("taskEventBus") EventBroker eventBroker,
            @Component("taskEventTopic") String topic) {
        super(eventBroker, topic);
    }

    @Override
    public void taskCreated(TaskId taskId) {
        var event = createEventModel(taskId, TaskEventAction.CREATED);
        this.publishEvent(event);
    }

    @Override
    public void taskDeleted(TaskId taskId) {
        var event = createEventModel(taskId, TaskEventAction.DELETED);
        this.publishEvent(event);
    }

    private TaskEvent createEventModel(TaskId taskId, TaskEventAction action) {
        return TaskEvent.builder().
                taskId(taskId.toString()).
                action(action.toString()).
                build();
    }
}
