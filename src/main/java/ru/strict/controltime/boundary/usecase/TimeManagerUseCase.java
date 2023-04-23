package ru.strict.controltime.boundary.usecase;

import ru.strict.controltime.boundary.model.CreateTaskParams;

import java.time.Duration;

public interface TimeManagerUseCase {
     void addTask(CreateTaskParams createTaskParams);
     void deleteTask(String taskId);
}
