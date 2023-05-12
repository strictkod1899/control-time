package ru.strict.controltime.timemanager.boundary.presenter;

import ru.strict.controltime.task.domain.entity.task.Task;

public interface NotificationPresenter {
    void showNotification(Task task);
}
