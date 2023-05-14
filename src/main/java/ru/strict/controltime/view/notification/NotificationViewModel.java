package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.view.boundary.BaseViewModel;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationViewModel extends BaseViewModel<NotificationViewState> {

    Task currentTaskForNotify;
    Set<TaskId> activeTaskNotifications;

    public NotificationViewModel() {
        activeTaskNotifications = new HashSet<>();
    }

    @Nonnull
    @Override
    protected NotificationViewState getUnknownState() {
        return NotificationViewState.none;
    }

    public boolean isActiveTaskNotification(TaskId taskId) {
        return activeTaskNotifications.contains(taskId);
    }

    public void addActiveTaskNotification(TaskId taskId) {
        activeTaskNotifications.add(taskId);
    }

    public void removeActiveTaskNotification(TaskId taskId) {
        activeTaskNotifications.remove(taskId);
    }
}
