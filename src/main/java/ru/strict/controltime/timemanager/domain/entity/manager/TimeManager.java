package ru.strict.controltime.timemanager.domain.entity.manager;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTasks;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PACKAGE)
public class TimeManager {
    TimeManagerId id;
    ManageTasks manageTasks;

    public static TimeManager init(List<Task> tasksList) {
        var manageTasks = ManageTasks.init(tasksList);

        var timeManager = new TimeManager();
        timeManager.id = TimeManagerId.init();
        timeManager.manageTasks = manageTasks;
        return timeManager;
    }

    public static TimeManager from(TimeManagerId id, List<ManageTask> manageTasksList) {
        if (id == null) {
            throw TimeManagerError.errTimeManagerIdIsRequired();
        }

        var manageTasks = ManageTasks.from(manageTasksList);

        var timeManager = new TimeManager();
        timeManager.id = id;
        timeManager.manageTasks = manageTasks;
        return timeManager;
    }

    public void addTask(Task task) {
        manageTasks.addTask(task);
    }

    public void deleteTask(TaskId taskId) {
        manageTasks.deleteTask(taskId);
    }

    public List<TaskId> getReadyTaskIds() {
        return manageTasks.toList().stream().
                filter(ManageTask::isReady).
                map(ManageTask::getTaskId).
                collect(Collectors.toList());
    }

    public Optional<Message> getTaskMessage(TaskId taskId) {
        return manageTasks.getManageTaskById(taskId).
                map(ManageTask::getTask).
                map(Task::getMessage);
    }

    public void markTaskAsProcessed(TaskId taskId) {
        var taskOptional = manageTasks.getManageTaskById(taskId);

        var task = taskOptional.orElseThrow(() -> TimeManagerError.errTaskNotFound(taskId));

        task.markAsProcessed();
    }

    public Collection<ManageTask> getManageTasks() {
        return manageTasks.toList();
    }

    public List<Task> getTasks() {
        return manageTasks.getTasks();
    }
}
