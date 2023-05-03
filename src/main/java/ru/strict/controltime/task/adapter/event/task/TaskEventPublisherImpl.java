package ru.strict.controltime.task.adapter.event.task;

import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.event.EventBroker;
import ru.strict.event.EventPublisher;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;

public class TaskEventPublisherImpl extends EventPublisher<TaskEvent> implements TaskEventPublisher {

    public TaskEventPublisherImpl(EventBroker<TaskEvent> eventBroker) {
        super(eventBroker);
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
