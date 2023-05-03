package ru.strict.controltime.common.task.boundary.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskEvent {
    String taskId;
    String action;
}
