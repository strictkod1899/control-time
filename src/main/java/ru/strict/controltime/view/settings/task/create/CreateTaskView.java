package ru.strict.controltime.view.settings.task.create;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.settings.task.create.component.CreateTaskWindow;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskEvent;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskTopic;
import ru.strict.controltime.view.settings.task.create.gateway.TaskGateway;
import ru.strict.event.EventBroker;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTaskView {

    final CreateTaskWindow window;

    final TaskGateway taskGateway;

    public CreateTaskView(
            TaskGateway taskGateway,
            EventBroker viewEventBroker,
            CreateTaskWindow.Params windowParams
    ) {
        viewEventBroker.subscribe(CreateTaskTopic.create, this::onCreateTask);

        this.taskGateway = taskGateway;
        this.window = new CreateTaskWindow(windowParams, viewEventBroker);
    }

    public void show() {
        this.window.show();
    }

    private void onCreateTask(Object event) {
        var createTaskEvent = (CreateTaskEvent) event;

        taskGateway.createTask(createTaskEvent.getMessage(), createTaskEvent.getDuration().toNanos());
    }
}
