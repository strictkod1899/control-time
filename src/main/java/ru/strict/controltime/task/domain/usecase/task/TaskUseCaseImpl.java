package ru.strict.controltime.task.domain.usecase.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.strict.controltime.common.task.domain.usecase.event.TaskEventUseCaseImpl;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.model.CreateTaskData;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.boundary.usecase.TaskUseCase;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.SleepDuration;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.validate.CommonValidator;

import java.util.List;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskUseCaseImpl implements TaskUseCase {

    TaskRepository taskRepository;
    TaskEventPublisher taskEventPublisher;

    public TaskUseCaseImpl(
            TaskRepository taskRepository,
            TaskEventPublisher taskEventPublisher) {
        CommonValidator.throwIfNull(taskRepository, "taskRepository");
        CommonValidator.throwIfNull(taskEventPublisher, "taskEventPublisher");

        this.taskRepository = taskRepository;
        this.taskEventPublisher = taskEventPublisher;
    }

    @Override
    public void addTask(CreateTaskData createTaskData) {
        if (createTaskData == null) {
            throw TaskUseCaseError.errCreateTaskDataIsRequired();
        }

        var taskMessage = Message.from(createTaskData.getMessage());
        var sleepDuration = SleepDuration.from(createTaskData.getSleepDuration());
        var task = Task.init(taskMessage, sleepDuration);

        taskRepository.insert(task);
        try {
            taskEventPublisher.taskCreated(task.getId());
        } catch (Exception ex) {
            log.error("fail publish event 'taskCreated'", ex);
        }
    }

    @Override
    public void deleteTask(String taskIdStr) {
        if (taskIdStr == null) {
            throw TaskUseCaseError.errTaskIdIsRequired();
        }

        var taskId = TaskId.from(taskIdStr);
        var taskOptional = taskRepository.getById(taskId);
        if (taskOptional.isEmpty()) {
            throw TaskUseCaseError.errTaskNotFoundById(taskId);
        }

        taskRepository.delete(taskId);
        try {
            taskEventPublisher.taskDeleted(taskId);
        } catch (Exception ex) {
            log.error("fail publish event 'taskDeleted'", ex);
        }
    }

    @Override
    public List<Task> getTasks() {
        return taskRepository.getAllTasks();
    }
}
