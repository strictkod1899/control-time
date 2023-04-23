package ru.strict.controltime.task.boundary.event;

import ru.strict.controltime.task.domain.entity.task.TaskId;

public interface TaskEventPublisher {
    void taskCreated(TaskId taskId);
    void taskDeleted(TaskId taskId);
}
