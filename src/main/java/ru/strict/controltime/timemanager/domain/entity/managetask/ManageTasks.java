package ru.strict.controltime.timemanager.domain.entity.managetask;

import lombok.EqualsAndHashCode;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class ManageTasks {
    private final Map<TaskId, ManageTask> tasksMap;

    public static ManageTasks init(List<Task> tasksList) {
        if (tasksList == null) {
            throw ManageTaskError.errTaskListIsRequired();
        }

        checkDoubleTasks(tasksList);

        var tasksMap = tasksList.stream().
                map(ManageTask::init).
                collect(Collectors.toMap(ManageTask::getTaskId, Function.identity()));

        return new ManageTasks(tasksMap);
    }

    public static ManageTasks from(List<ManageTask> manageTasksList) {
        if (manageTasksList == null) {
            throw ManageTaskError.errManageTaskListIsRequired();
        }

        var tasksList = manageTasksList.stream().
                map(ManageTask::getTask).
                collect(Collectors.toList());
        checkDoubleTasks(tasksList);

        var tasksMap = manageTasksList.stream().
                collect(Collectors.toMap(ManageTask::getTaskId, Function.identity()));

        return new ManageTasks(tasksMap);
    }

    private ManageTasks(Map<TaskId, ManageTask> tasksMap) {
        this.tasksMap = tasksMap;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw ManageTaskError.errTaskIsRequired();
        }

        checkDoubleTask(task);

        var manageTask = ManageTask.init(task);
        this.tasksMap.put(task.getId(), manageTask);
    }

    public void deleteTask(TaskId taskId) {
        if (taskId == null) {
            throw ManageTaskError.errTaskIdIsRequired();
        }

        if (!tasksMap.containsKey(taskId)) {
            throw ManageTaskError.errTaskNotFoundById(taskId);
        }

        tasksMap.remove(taskId);
    }

    public List<Task> getTasks() {
        return this.tasksMap.values().stream().
                map(ManageTask::getTask).
                collect(Collectors.toList());
    }

    public Optional<ManageTask> getManageTaskById(TaskId taskId) {
        if (taskId == null) {
            throw ManageTaskError.errTaskIdIsRequired();
        }

        return Optional.ofNullable(tasksMap.get(taskId));
    }

    public List<ManageTask> toList() {
        return tasksMap.values().stream().toList();
    }

    private void checkDoubleTask(Task task) {
        if (this.tasksMap.containsKey(task.getId())) {
            throw ManageTaskError.errDoubledTaskById(task.getId());
        }
    }

    private static void checkDoubleTasks(List<Task> tasksList) {
        var taskIdsSet = new HashSet<TaskId>();

        tasksList.forEach(task -> {
            if (taskIdsSet.contains(task.getId())) {
                throw ManageTaskError.errDoubledTaskById(task.getId());
            }

            taskIdsSet.add(task.getId());
        });
    }

}
