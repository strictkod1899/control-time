package ru.strict.controltime.task.adapter.repository.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.SleepDuration;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.time.Duration;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class TaskModel {
    @JsonProperty("id")
    String id;
    @JsonProperty("message")
    String message;
    @JsonProperty("sleepDurationNanos")
    long sleepDurationNanos;

    public static TaskModel ToTaskModel(Task task) {
        var taskModel = new TaskModel();
        taskModel.id = task.getId().toString();
        taskModel.message = task.getMessage().toString();
        taskModel.sleepDurationNanos = task.getSleepDuration().toNanos();

        return taskModel;
    }

    public static Task ToTaskEntity(TaskModel taskJson) {
        var taskId = TaskId.from(taskJson.id);
        var message = Message.from(taskJson.message);
        var sleepDuration = SleepDuration.from(Duration.ofNanos(taskJson.sleepDurationNanos));

        return Task.builder().
                id(taskId).
                message(message).
                sleepDuration(sleepDuration).
                build();
    }
}
