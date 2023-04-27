package ru.strict.controltime.task.adapter.repository.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.initservice.InitializableRepository;
import ru.strict.controltime.task.adapter.repository.task.model.TaskModel;
import ru.strict.controltime.task.adapter.repository.task.model.TasksModel;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TaskJsonRepository implements
        TaskRepository,
        ru.strict.controltime.timemanager.boundary.repository.TaskRepository,
        InitializableRepository {

    JacksonObjectMapper jacksonObjectMapper;
    String filePath;

    @Override
    public void init() {
        FileUtil.createFileIfNotExists(filePath, getEmptyTasksJsonBytes());
    }

    @Override
    public void insert(Task task) {
        var tasksJson = readTasksJson();

        var newTaskJson = TaskModel.ToTaskModel(task);
        tasksJson.tasks.add(newTaskJson);

        jacksonObjectMapper.writeToFile(filePath, tasksJson);
    }

    @Override
    public void delete(TaskId taskId) {
        var tasksJson = readTasksJson();

        var taskIndexOptional = getTaskIndexById(tasksJson, taskId);
        var taskIndex = taskIndexOptional.orElseThrow(() -> TaskJsonRepositoryError.errTaskNotFoundById(taskId));

        tasksJson.tasks.remove((int)taskIndex);

        jacksonObjectMapper.writeToFile(filePath, tasksJson);
    }

    @Override
    public Optional<Task> getById(TaskId taskId) {
        var tasksJson = readTasksJson();
        var taskIndexOptional = getTaskIndexById(tasksJson, taskId);

        if (taskIndexOptional.isEmpty()) {
            return Optional.empty();
        }

        var taskIndex = taskIndexOptional.get();
        var taskJson = tasksJson.tasks.get(taskIndex);
        var task = TaskModel.ToTaskEntity(taskJson);

        return Optional.of(task);
    }

    @Override
    public List<Task> getAllTasks() {
        var tasksJson = readTasksJson();
        return TasksModel.ToTaskEntities(tasksJson);
    }

    private Optional<Integer> getTaskIndexById(TasksModel tasksJson, TaskId taskId) {
        Integer taskIndex = null;
        for (var i = 0; i < tasksJson.tasks.size(); i++) {
            var task = tasksJson.tasks.get(i);
            if (task.id.equals(taskId.toString())) {
                taskIndex = i;
                break;
            }
        }

        return Optional.ofNullable(taskIndex);
    }

    private TasksModel readTasksJson() {
        var tasksJson = jacksonObjectMapper.readFromFile(filePath, TasksModel.class);
        if (tasksJson.tasks == null) {
            tasksJson.tasks = new ArrayList<>(1);
        }

        return tasksJson;
    }

    private byte[] getEmptyTasksJsonBytes() {
        var emptyTasksModel = TasksModel.ToTasksModel(List.of());
        try {
            return jacksonObjectMapper.writeValueAsBytes(emptyTasksModel);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static TaskJsonRepositoryBuilder builder() {
        return new TaskJsonRepositoryBuilder();
    }
}
