package ru.strict.controltime.common.task.domain.usecase.event.handler;

import ru.strict.controltime.common.task.boundary.model.TaskEvent;

public interface TaskEventHandler {
    void handle(TaskEvent event);
}
