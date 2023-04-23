package ru.strict.controltime.task.boundary.usecase;

import ru.strict.controltime.task.boundary.model.CreateTaskData;

public interface TaskUseCase {
     void addTask(CreateTaskData createTaskData);
     void deleteTask(String taskId);
}
