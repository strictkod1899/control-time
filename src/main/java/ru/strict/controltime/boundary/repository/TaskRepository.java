package ru.strict.controltime.boundary.repository;

import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskId;

import java.util.List;

public interface TaskRepository {
    void insert(Task task);
    void update(Task task);
    void delete(TaskId taskId);
    Task getById(TaskId taskId);
    List<Task> getAllTasks();
}
