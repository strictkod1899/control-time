package ru.strict.controltime.timemanager.boundary.repository;

import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> getById(TaskId taskId);
    List<Task> getAllTasks();
}
