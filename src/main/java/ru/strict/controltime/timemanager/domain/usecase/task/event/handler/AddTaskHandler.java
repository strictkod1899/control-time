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
import ru.strict.validate.CommonValidator;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddTaskHandler implements TaskEventHandler {

    TaskRepository taskRepository;
    TimeManagerRepository timeManagerRepository;
    TimeManagerPresenter timeManagerPresenter;

    public AddTaskHandler(
            TaskRepository taskRepository,
            TimeManagerRepository timeManagerRepository,
            TimeManagerPresenter timeManagerPresenter) {
        CommonValidator.throwIfNull(taskRepository, "taskRepository");
        CommonValidator.throwIfNull(timeManagerRepository, "timeManagerRepository");
        CommonValidator.throwIfNull(timeManagerPresenter, "timeManagerPresenter");

        this.taskRepository = taskRepository;
        this.timeManagerRepository = timeManagerRepository;
        this.timeManagerPresenter = timeManagerPresenter;
    }

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
}
