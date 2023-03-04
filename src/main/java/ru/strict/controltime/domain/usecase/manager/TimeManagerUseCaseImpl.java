package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.boundary.model.CreateTaskParams;
import ru.strict.controltime.boundary.usecase.TimeManagerUseCase;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerUseCaseImpl implements TimeManagerUseCase {
    @Override
    public void initTimeManager(List<CreateTaskParams> tasksParams) {

    }

    @Override
    public void addTask(CreateTaskParams createTaskParams) {

    }

    @Override
    public void deleteTask(String taskId) {

    }

    @Override
    public List<String> getReadyTaskIds() {
        return null;
    }

    @Override
    public String getTaskMessage(String taskId) {
        return null;
    }

    @Override
    public void markTaskAsProcessed(String taskId) {

    }
}
