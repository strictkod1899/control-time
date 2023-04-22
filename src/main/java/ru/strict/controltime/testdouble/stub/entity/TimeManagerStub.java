package ru.strict.controltime.testdouble.stub.entity;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.manager.TimeManagerId;
import ru.strict.controltime.domain.entity.managetask.ManageTask;
import ru.strict.controltime.domain.entity.task.Task;

import java.util.List;

@UtilityClass
public class TimeManagerStub {

    public TimeManager getTimeManagerWithoutReadyTasks() {
        var manageTasksList = List.of(ManageTaskStub.getBaseManageTask(), ManageTaskStub.getFullManageTask());

        return getTimeManagerFrom(manageTasksList);
    }

    public TimeManager getFullTimeManager() {
        var tasksList = List.of(
                ManageTaskStub.getBaseManageTask(),
                ManageTaskStub.getFullManageTask(),
                ManageTaskStub.getReadyBaseManageTask(),
                ManageTaskStub.getReadyFullManageTask()
        );

        return getTimeManagerFrom(tasksList);
    }

    public TimeManager getTimeManagerFrom(List<ManageTask> manageTaskList) {
        return TimeManager.from(getId(), manageTaskList);
    }

    public TimeManager getEmptyTimeManager() {
        return TimeManager.init(List.of());
    }

    public TimeManagerId getId() {
        return TimeManagerId.init();
    }
}
