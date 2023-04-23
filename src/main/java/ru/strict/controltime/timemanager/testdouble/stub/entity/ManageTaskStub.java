package ru.strict.controltime.timemanager.testdouble.stub.entity;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTaskBuilder;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTasks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@UtilityClass
public class ManageTaskStub {

    public ManageTasks getFullManageTasks() {
        var tasksList = List.of(
                getBaseManageTask(),
                getFullManageTask(),
                getReadyBaseManageTask(),
                getReadyFullManageTask()
        );

        return ManageTasks.from(tasksList);
    }

    public ManageTasks getEmptyManageTasks() {
        return ManageTasks.init(List.of());
    }

    public ManageTask getReadyFullManageTask() {
        var task = TaskStub.getTask();
        var givenStartedAt = Instant.now().minus(task.getSleepDuration().toMinutes()*2+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(task.getSleepDuration().toMinutes()+1, ChronoUnit.MINUTES);

        return getFullManageTaskBuilder().
                task(task).
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                build();
    }

    public ManageTask getReadyBaseManageTask() {
        var task = TaskStub.getTask();
        var startedAt = Instant.now().minus(task.getSleepDuration().toMinutes()+1, ChronoUnit.MINUTES);

        return getBaseManageTaskBuilder().
                task(task).
                startedAt(startedAt).
                build();
    }

    public ManageTask getFullManageTask() {
        return getFullManageTaskBuilder().build();
    }

    public ManageTask getBaseManageTask() {
        return getBaseManageTaskBuilder().build();
    }

    public ManageTaskBuilder getFullManageTaskBuilder() {
        var task = TaskStub.getTask();
        var startedAt = Instant.now().minus(task.getSleepDuration().toMinutes()+5, ChronoUnit.MINUTES);
        var lastProcessedAt = startedAt.plus(task.getSleepDuration().toMinutes(), ChronoUnit.MINUTES);

        return ManageTask.builder().
                task(task).
                startedAt(startedAt).
                lastProcessedAt(lastProcessedAt);
    }

    public ManageTaskBuilder getBaseManageTaskBuilder() {
        var task = TaskStub.getTask();
        var startedAt = Instant.now().minus(task.getSleepDuration().toMinutes()-5, ChronoUnit.MINUTES);

        return ManageTask.builder().
                task(task).
                startedAt(startedAt);
    }
}
