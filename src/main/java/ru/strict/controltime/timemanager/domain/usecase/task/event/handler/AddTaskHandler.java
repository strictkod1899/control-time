package ru.strict.controltime.timemanager.domain.usecase.task.event.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.strict.controltime.common.task.boundary.model.TaskEvent;
import ru.strict.controltime.common.task.domain.usecase.event.handler.TaskEventHandler;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.boundary.repository.TaskRepository;
import ru.strict.controltime.timemanager.boundary.repository.TimeManagerRepository;

@Slf4j
@FieldDefaults(level = AccessLevel.PACKAGE)
public class AddTaskHandler implements TaskEventHandler {

    TaskRepository taskRepository;
    TimeManagerRepository timeManagerRepository;
    TimeManagerPresenter timeManagerPresenter;

    @Override
    public void handle(TaskEvent event) {
        var taskId = TaskId.from(event.getTaskId());

        var taskOptional = taskRepository.getById(taskId);
        var task = taskOptional.orElseThrow(() -> TaskHandlerError.errTaskNotFoundById(taskId));

        var timeManagerOptional = timeManagerRepository.getActiveManager();
        var timeManager = timeManagerOptional.orElseThrow(TaskHandlerError::errActiveTimeManagerNotFound);

        timeManager.addTask(task);

        timeManagerRepository.setActiveManager(timeManager);
        timeManagerPresenter.refreshTimeManager(timeManager);
    }

    public static AddTaskHandlerBuilder builder() {
        return new AddTaskHandlerBuilder();
    }
}
