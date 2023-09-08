package ru.strict.controltime.view.settings.task.create.event;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;

@Value
@Builder
public class CreateTaskEvent {
    String message;
    Duration duration;
}
