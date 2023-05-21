package ru.strict.controltime.view.settings.component.tasks;

public interface SettingsListener {
    void taskCreated(String message, long sleepDurationNanos);
}
