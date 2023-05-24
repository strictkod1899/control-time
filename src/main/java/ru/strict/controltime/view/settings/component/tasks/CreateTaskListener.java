package ru.strict.controltime.view.settings.component.tasks;

public interface CreateTaskListener {
    void createTask(String message, long sleepDurationNanos);
}
