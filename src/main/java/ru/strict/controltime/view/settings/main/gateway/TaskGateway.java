package ru.strict.controltime.view.settings.main.gateway;

import ru.strict.controltime.task.domain.entity.task.Task;

import java.util.List;

public interface TaskGateway {
    List<Task> getActualTasks();
}
