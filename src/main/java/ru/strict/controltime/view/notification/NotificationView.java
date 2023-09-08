package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.validate.CommonValidator;
import ru.strict.view.notification.NotificationListener;
import ru.strict.view.notification.NotificationWindow;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationView {

    NotificationWindow window;

    NotificationViewController.NotificationListener notificationListener;
    Task task;

    public NotificationView(Task task, NotificationViewController.NotificationListener notificationListener) {
        CommonValidator.throwIfNull(task, "task");
        CommonValidator.throwIfNull(notificationListener, "notificationListener");

        this.notificationListener = notificationListener;
        this.task = task;

        var notificationParams = NotificationWindow.Params.createDefault(false);
        var notificationListenerWrapper = new NotificationListenerWrapper();
        this.window = new NotificationWindow(task.getMessage().toString(),
                notificationParams,
                notificationListenerWrapper);
    }

    public void show() {
        this.window.show();
    }

    private class NotificationListenerWrapper implements NotificationListener {

        @Override
        public void close() {
            window.hide();
            notificationListener.close(task.getId());
        }
    }
}
