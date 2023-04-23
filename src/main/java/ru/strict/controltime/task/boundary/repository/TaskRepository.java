package ru.strict.controltime.task.boundary.repository;

import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.List;

public interface TaskRepository {
    void insert(Task task);
    void update(Task task);
    void delete(TaskId taskId);
    Task getById(TaskId taskId);
    List<Task> getAllTasks();
}
