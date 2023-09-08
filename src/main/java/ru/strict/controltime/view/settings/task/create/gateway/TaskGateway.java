package ru.strict.controltime.view.settings.task.create.gateway;

public interface TaskGateway {
    void createTask(String message, long sleepDurationNanos);
}
