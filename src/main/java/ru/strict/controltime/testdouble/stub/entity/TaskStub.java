package ru.strict.controltime.testdouble.stub.entity;

import lombok.experimental.UtilityClass;
import ru.strict.controltime.domain.entity.task.Message;
import ru.strict.controltime.domain.entity.task.Task;
import ru.strict.controltime.domain.entity.task.TaskBuilder;
import ru.strict.controltime.domain.entity.task.TaskId;
import ru.strict.test.RandomUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class TaskStub {

    public Task getReadyFullTask() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()*2+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        return getFullTaskBuilder().
                sleepDuration(givenDuration).
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt).
                build();
    }

    public Task getFullTask() {
        return getFullTaskBuilder().build();
    }

    public Task getReadyBaseTask() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+1, ChronoUnit.MINUTES);

        return getBaseTaskBuilder().
                sleepDuration(givenDuration).
                startedAt(givenStartedAt).
                build();
    }

    public Task getBaseTask() {
        return getBaseTaskBuilder().build();
    }

    public TaskBuilder getFullTaskBuilder() {
        var givenDuration = Duration.ofMinutes(20);
        var givenStartedAt = Instant.now().minus(givenDuration.toMinutes()+5, ChronoUnit.MINUTES);
        var givenLastProcessedAt = givenStartedAt.plus(givenDuration.toMinutes(), ChronoUnit.MINUTES);

        return new TaskBuilder().
                id(getTaskId()).
                message(getMessage()).
                sleepDuration(givenDuration).
                startedAt(givenStartedAt).
                lastProcessedAt(givenLastProcessedAt);
    }

    public TaskBuilder getBaseTaskBuilder() {
        return new TaskBuilder().
                id(getTaskId()).
                message(getMessage()).
                sleepDuration(Duration.ofMinutes(30));
    }

    public TaskId getTaskId() {
        return TaskId.init();
    }

    public Message getMessage() {
        return Message.from(RandomUtil.generateDefaultStr());
    }
}