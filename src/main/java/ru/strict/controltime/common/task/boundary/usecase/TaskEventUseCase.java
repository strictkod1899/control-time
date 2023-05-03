package ru.strict.controltime.common.task.boundary.usecase;

import ru.strict.controltime.common.task.boundary.model.TaskEvent;

public interface TaskEventUseCase {
    void processEvent(TaskEvent event);
}
