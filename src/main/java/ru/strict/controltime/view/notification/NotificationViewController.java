package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationViewController {

    NotificationView view;
    NotificationViewModel model;

    public NotificationViewController() {
        this.model = new NotificationViewModel();
        this.view = new NotificationView(model);
    }

    public void showNotificationFromTask(Task task) {
        model.setCurrentTaskForNotify(task);
        view.refresh(NotificationViewState.showNotification);
    }
}
