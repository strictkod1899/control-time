package ru.strict.controltime.task.boundary.repository;

import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void insert(Task task);
    void delete(TaskId taskId);
    Optional<Task> getById(TaskId taskId);
    List<Task> getAllTasks();
}
