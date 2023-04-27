package ru.strict.controltime.task.adapter.repository.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;

import java.util.List;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class TasksModel {
    @JsonProperty("tasks")
    List<TaskModel> tasks;

    public static TasksModel ToTasksModel(List<Task> tasks) {
        var taskListJson = tasks.stream().
                map(TaskModel::ToTaskModel).
                toList();

        var tasksModel = new TasksModel();
        tasksModel.tasks = taskListJson;

        return tasksModel;
    }

    public static List<Task> ToTaskEntities(TasksModel tasksJson) {
        if (tasksJson.tasks == null) {
            tasksJson.tasks = List.of();
        }

        return tasksJson.tasks.stream().
                map(TaskModel::ToTaskEntity).
                toList();
    }
}
