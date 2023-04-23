package ru.strict.controltime.boundary.presenter;

import ru.strict.controltime.domain.entity.task.Message;

public interface NotificationPresenter {
    void showMessage(Message message);
}
