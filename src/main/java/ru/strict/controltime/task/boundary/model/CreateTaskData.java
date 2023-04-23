package ru.strict.controltime.task.boundary.model;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
public class CreateTaskData {
    String message;
    Duration sleepDuration;
}
