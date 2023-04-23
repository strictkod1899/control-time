package ru.strict.controltime.task.boundary.event;

import ru.strict.controltime.task.domain.entity.task.TaskId;

public interface TaskEventPublisher {
    void TaskCreated(TaskId taskId);
    void TaskDeleted(TaskId taskId);
}
