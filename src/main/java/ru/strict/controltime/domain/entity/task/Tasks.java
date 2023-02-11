package ru.strict.controltime.domain.entity.task;

import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Tasks {
    private final Map<TaskId, Task> tasksMap;

    public static Tasks init() {
        return new Tasks();
    }

    public static Tasks from(List<Task> tasksList) {
        if (tasksList == null) {
            throw TaskError.errTasksListIsRequired();
        }

        checkDoubleTasks(tasksList);

        var tasksMap = tasksList.stream().
                collect(Collectors.toMap(Task::getId, Function.identity()));
        return new Tasks(tasksMap);
    }

    private Tasks() {
        this.tasksMap = new HashMap<>();
    }

    private Tasks(Map<TaskId, Task> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw TaskError.errTaskIsRequired();
        }

        checkDoubleTask(task);

        this.tasksMap.put(task.getId(), task);
    }

    public Optional<Task> getTaskById(TaskId taskId) {
        if (taskId == null) {
            throw TaskError.errTaskIdIsRequired();
        }

        return Optional.ofNullable(tasksMap.get(taskId));
    }

    public Collection<Task> toCollection() {
        return tasksMap.values();
    }

    private void checkDoubleTask(Task task) {
        if (this.tasksMap.containsKey(task.getId())) {
            throw TaskError.errDoubledTaskById(task.getId());
        }
    }

    private static void checkDoubleTasks(List<Task> tasksList) {
        var taskIdsSet = new HashSet<TaskId>();

        tasksList.forEach(task -> {
            if (taskIdsSet.contains(task.getId())) {
                throw TaskError.errDoubledTaskById(task.getId());
            }

            taskIdsSet.add(task.getId());
        });
    }

}
