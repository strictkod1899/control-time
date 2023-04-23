package ru.strict.controltime.timemanager.boundary.presenter;

import ru.strict.controltime.task.domain.entity.task.Message;

public interface NotificationPresenter {
    void showMessage(Message message);
}
