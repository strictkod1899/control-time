package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationViewController {

    Set<TaskId> activeTaskNotifications;
    NotificationListener notificationListener;

    public NotificationViewController() {
        this.activeTaskNotifications = new HashSet<>();
        this.notificationListener = new NotificationListener();
    }

    public void showNotificationForTask(Task task) {
        if (isActiveTaskNotification(task.getId())) {
            return;
        }

        this.activeTaskNotifications.add(task.getId());

        new NotificationView(task, notificationListener).show();
    }

    public boolean isActiveTaskNotification(TaskId taskId) {
        return activeTaskNotifications.contains(taskId);
    }

    class NotificationListener {

        public void close(TaskId taskId) {
            activeTaskNotifications.remove(taskId);
        }
    }
}
