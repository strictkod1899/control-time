package ru.strict.controltime.view.settings.gateway;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.adapter.controller.inner.task.TaskInnerController;
import ru.strict.controltime.task.adapter.controller.inner.task.request.CreateTaskRequest;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.validate.CommonValidator;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskGatewayImpl implements TaskGateway {

    TaskInnerController taskInnerController;

    public TaskGatewayImpl(TaskInnerController taskInnerController) {
        CommonValidator.throwIfNull(taskInnerController, "taskInnerController");

        this.taskInnerController = taskInnerController;
    }

    @Override
    public List<Task> getActualTasks() {
        return taskInnerController.getActiveTasks();
    }

    @Override
    public void createTask(String message, long sleepDurationNanos) {
        var createTaskRequest = CreateTaskRequest.builder().
                message(message).
                sleepDurationNanos(sleepDurationNanos).
                build();

        taskInnerController.createTask(createTaskRequest);
    }
}
