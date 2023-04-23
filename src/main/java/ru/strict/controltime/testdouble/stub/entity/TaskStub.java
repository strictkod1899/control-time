package ru.strict.controltime.testdouble.stub.entity;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.SleepDuration;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskBuilder;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.test.RandomUtil;

import java.time.Duration;
import java.util.List;

@UtilityClass
public class TaskStub {

    public List<Task> getTasks() {
        return List.of(getTask(), getTask(), getTask());
    }

    public Task getTask() {
        return getTaskBuilder().build();
    }
    public Task getTaskWithId(TaskId id) {
        return getTaskBuilder().
                id(id).
                build();
    }

    public TaskBuilder getTaskBuilder() {
        return Task.builder().
                id(getId()).
                message(getMessage()).
                sleepDuration(getSleepDuration());
    }

    public SleepDuration getSleepDuration() {
        return getSleepDurationFrom(Duration.ofMinutes(30));
    }

    public SleepDuration getSleepDurationFrom(Duration duration) {
        return SleepDuration.from(duration);
    }

    public Message getMessage() {
        return Message.from(RandomUtil.generateDefaultStr());
    }

    public TaskId getId() {
        return TaskId.init();
    }
}
