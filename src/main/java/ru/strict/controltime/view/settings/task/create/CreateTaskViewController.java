package ru.strict.controltime.view.settings.task.create;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.settings.task.create.component.CreateTaskWindow;
import ru.strict.controltime.view.settings.task.create.gateway.TaskGateway;
import ru.strict.event.EventBroker;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateTaskViewController {

    final TaskGateway taskGateway;
    final EventBroker viewEventBroker;

    public CreateTaskViewController(TaskGateway taskGateway, EventBroker viewEventBroker) {
        this.taskGateway = taskGateway;
        this.viewEventBroker = viewEventBroker;
    }

    public void showCreateTask(CreateTaskWindow.Params windowParams) {
        var createTaskView = new CreateTaskView(taskGateway, viewEventBroker, windowParams);
        createTaskView.show();
    }
}
