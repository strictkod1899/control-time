package ru.strict.controltime.task.adapter.controller.inner.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.adapter.controller.inner.task.request.CreateTaskRequest;
import ru.strict.controltime.task.boundary.usecase.TaskUseCase;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.validate.CommonValidator;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskInnerController {
    TaskUseCase taskUseCase;

    public TaskInnerController(TaskUseCase taskUseCase) {
        CommonValidator.throwIfNull(taskUseCase, "taskUseCase");

        this.taskUseCase = taskUseCase;
    }

    public List<Task> getActiveTasks() {
        return taskUseCase.getTasks();
    }

    public void createTask(CreateTaskRequest request) {
        taskUseCase.createTask(request.toCreateTaskData());
    }
}
