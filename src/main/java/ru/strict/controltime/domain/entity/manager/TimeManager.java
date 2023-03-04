package ru.strict.controltime.domain.entity.manager;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskBuilder;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.controltime.domain.entity.task.Tasks;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManager {
    TimeManagerId id;
    Tasks tasks;

    public static TimeManager init() {
        return new TimeManager();
    }

    public static TimeManager from(TimeManagerId id, List<Task> tasksList) {
        if (id == null) {
            throw TimeManagerError.errTimeManagerId();
        }

        var tasks = Tasks.from(tasksList);

        return new TimeManager(id, tasks);
    }

    private TimeManager() {
        this.id = TimeManagerId.init();
        this.tasks = Tasks.init();
    }

    private TimeManager(TimeManagerId id, Tasks tasks) {
        this.id = id;
        this.tasks = tasks;
    }

    public void addTask(Message message, Duration sleepDuration) {
        var task = new TaskBuilder().
                id(TaskId.init()).
                message(message).
                sleepDuration(sleepDuration).
                build();

        this.tasks.addTask(task);
    }

    public void deleteTask(TaskId taskId) {
        this.tasks.deleteTask(taskId);
    }

    public List<TaskId> getReadyTaskIds() {
        return tasks.toCollection().stream().
                filter(Task::isReady).
                map(Task::getId).
                collect(Collectors.toList());
    }

    public Optional<Message> getTaskMessage(TaskId taskId) {
        return this.tasks.getTaskById(taskId).
                map(Task::getMessage);
    }

    public void markTaskAsProcessed(TaskId taskId) {
        var taskOptional = this.tasks.getTaskById(taskId);

        var task = taskOptional.orElseThrow(() -> TimeManagerError.errTaskNotFound(taskId));

        task.markAsProcessed();
    }

    public Collection<Task> getTasks() {
        return tasks.toCollection();
    }
}
