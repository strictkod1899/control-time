package ru.strict.controltime.task.domain.usecase.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.boundary.event.TaskEventPublisher;
import ru.strict.controltime.task.boundary.model.CreateTaskData;
import ru.strict.controltime.task.boundary.repository.TaskRepository;
import ru.strict.controltime.task.boundary.usecase.TaskUseCase;
import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.task.domain.entity.task.SleepDuration;
import ru.strict.controltime.task.domain.entity.task.Task;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TaskUseCaseImpl implements TaskUseCase {

    TaskRepository taskRepository;
    TaskEventPublisher taskEventPublisher;

    @Override
    public void addTask(CreateTaskData createTaskData) {
        if (createTaskData == null) {
            throw TaskUseCaseError.errCreateTaskDataIsRequired();
        }

        var taskMessage = Message.from(createTaskData.getMessage());
        var sleepDuration = SleepDuration.from(createTaskData.getSleepDuration());
        var task = Task.init(taskMessage, sleepDuration);

        taskRepository.insert(task);
    }

    @Override
    public void deleteTask(String taskId) {
        throw new UnsupportedOperationException("impl me");
    }

    public static TaskUseCaseBuilder builder() {
        return new TaskUseCaseBuilder();
    }
}
