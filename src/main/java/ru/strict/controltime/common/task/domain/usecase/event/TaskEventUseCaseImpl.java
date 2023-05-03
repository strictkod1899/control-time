package ru.strict.controltime.common.task.domain.usecase.event;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.common.task.boundary.model.TaskEventAction;
import ru.strict.controltime.common.task.boundary.usecase.TaskEventUseCase;
import ru.strict.controltime.common.task.domain.usecase.event.handler.TaskEventHandlerProvider;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskEventUseCaseImpl implements TaskEventUseCase {

    TaskEventHandlerProvider handlerProvider;

    public static TaskEventUseCaseImpl init(TaskEventHandlerProvider handlerProvider) {
        var useCase = new TaskEventUseCaseImpl();
        useCase.handlerProvider = handlerProvider;

        return useCase;
    }

    @Override
    public void processEvent(TaskEvent event) {
        if (event == null) {
            throw TaskEventUseCaseError.errEventIsRequired();
        }

        var eventAction = TaskEventAction.of(event.getAction());
        var handlers = handlerProvider.getHandlersForAction(eventAction);
        handlers.forEach(handler -> handler.handle(event));
    }
}
