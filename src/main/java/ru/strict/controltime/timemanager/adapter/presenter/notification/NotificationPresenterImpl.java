package ru.strict.controltime.timemanager.adapter.presenter.notification;

import ru.strict.controltime.task.domain.entity.task.Message;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.view.swing.NotificationView;

public class NotificationPresenterImpl implements NotificationPresenter {

    public NotificationPresenterImpl() {}

    @Override
    public void showMessage(Message message) {
        var notificationView = new NotificationView(message.toString());
        notificationView.show();
    }
}
