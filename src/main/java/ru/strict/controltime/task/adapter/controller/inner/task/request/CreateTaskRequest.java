package ru.strict.controltime.task.adapter.controller.inner.task.request;

import lombok.Builder;
import lombok.Value;
import ru.strict.controltime.task.boundary.model.CreateTaskData;

import java.time.Duration;

@Value
@Builder
public class CreateTaskRequest {
    String message;
    Long sleepDurationNanos;

    public CreateTaskData toCreateTaskData() {
        return CreateTaskData.builder().
                message(this.message).
                sleepDuration(Duration.ofNanos(sleepDurationNanos)).
                build();
    }
}
