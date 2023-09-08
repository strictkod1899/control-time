package ru.strict.controltime.timemanager.adapter.presenter.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.timemanager.boundary.presenter.NotificationPresenter;
import ru.strict.controltime.view.notification.NotificationView;
import ru.strict.controltime.view.notification.NotificationViewController;
import ru.strict.view.swing.NotificationWindow;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationPresenterImpl implements NotificationPresenter {

    NotificationViewController notificationViewController;

    public NotificationPresenterImpl(NotificationViewController notificationViewController) {
        this.notificationViewController = notificationViewController;
    }

    @Override
    public void showNotification(Task task) {
        notificationViewController.showNotificationForTask(task);
    }
}
