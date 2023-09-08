package ru.strict.controltime.timemanager.adapter.event.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.common.task.boundary.usecase.TaskEventUseCase;
import ru.strict.event.EventListener;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEventListener implements EventListener {

    TaskEventUseCase taskEventUseCase;

    public static TaskEventListener init(TaskEventUseCase taskEventUseCase) {
        var listener = new TaskEventListener();
        listener.taskEventUseCase = taskEventUseCase;

        return listener;
    }

    @Override
    public void processEvent(Object event) {
        taskEventUseCase.processEvent((TaskEvent)event);
    }
}
