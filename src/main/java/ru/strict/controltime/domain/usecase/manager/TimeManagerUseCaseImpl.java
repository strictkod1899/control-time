package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.boundary.model.CreateTaskParams;
import ru.strict.controltime.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.Task;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase {

    TimeManager timeManager;

    @Override
    public void initTimeManager(List<CreateTaskParams> tasksParams) {
        if (tasksParams == null) {
            throw TimeManagerUseCaseError.errCreateTaskParamsListIsRequired();
        }

        var newTasks = tasksParams.stream().
                map(this::createTask).
                collect(Collectors.toList());

        if (TimeManagerSingleton.isInitialized()) {
            timeManager = TimeManagerSingleton.getInstance();
        } else {
            timeManager = TimeManagerSingleton.init(newTasks);
        }
    }

    @Override
    public void addTask(CreateTaskParams createTaskParams) {
        throw new UnsupportedOperationException("impl me");
    }

    @Override
    public void deleteTask(String taskId) {
        throw new UnsupportedOperationException("impl me");
    }

    @Override
    public List<String> getReadyTaskIds() {
        throw new UnsupportedOperationException("impl me");
    }

    @Override
    public String getTaskMessage(String taskId) {
        throw new UnsupportedOperationException("impl me");
    }

    @Override
    public void markTaskAsProcessed(String taskId) {
        throw new UnsupportedOperationException("impl me");
    }

    private Task createTask(CreateTaskParams taskParams) {
        var message = Message.from(taskParams.getMessage());

        return Task.init(message, taskParams.getSleepDuration());
    }
}
