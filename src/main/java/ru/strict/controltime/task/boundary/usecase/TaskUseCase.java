package ru.strict.controltime.task.boundary.usecase;

import ru.strict.controltime.task.boundary.model.CreateTaskData;
import ru.strict.controltime.task.domain.entity.task.Task;

import java.util.List;

public interface TaskUseCase {
     void addTask(CreateTaskData createTaskData);
     void deleteTask(String taskId);
     List<Task> getTasks();
}
