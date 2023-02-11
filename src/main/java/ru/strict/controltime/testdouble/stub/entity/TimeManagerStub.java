package ru.strict.controltime.testdouble.stub.entity;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.manager.TimeManagerId;
import ru.strict.controltime.domain.entity.task.Task;

import java.util.List;

@UtilityClass
public class TimeManagerStub {

    public TimeManager getTimeManagerWithoutReadyTasks() {
        var tasksList = List.of(TaskStub.getBaseTask(), TaskStub.getFullTask());

        return getTimeManagerFrom(tasksList);
    }

    public TimeManager getFullTimeManager() {
        var tasksList = List.of(
                TaskStub.getBaseTask(),
                TaskStub.getFullTask(),
                TaskStub.getReadyBaseTask(),
                TaskStub.getReadyFullTask()
        );

        return getTimeManagerFrom(tasksList);
    }

    public TimeManager getTimeManagerFrom(List<Task> tasksList) {
        return TimeManager.from(getId(), tasksList);
    }

    public TimeManager getBaseTimeManager() {
        return TimeManager.init();
    }

    public TimeManagerId getId() {
        return TimeManagerId.init();
    }
}
