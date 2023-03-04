package ru.strict.controltime.boundary.usecase;

import ru.strict.controltime.boundary.model.CreateTaskParams;

import java.util.List;

public interface TimeManagerUseCase {
     void initTimeManager(List<CreateTaskParams> tasksParams);
     void addTask(CreateTaskParams createTaskParams);
     void deleteTask(String taskId);
     List<String> getReadyTaskIds();
     String getTaskMessage(String taskId);
     void markTaskAsProcessed(String taskId);
}
