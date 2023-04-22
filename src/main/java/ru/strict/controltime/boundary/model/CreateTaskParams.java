package ru.strict.controltime.boundary.model;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
public class CreateTaskParams {
    String message;
    Duration sleepDuration;
}
