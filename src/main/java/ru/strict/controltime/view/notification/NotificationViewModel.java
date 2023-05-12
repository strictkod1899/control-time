package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.view.boundary.ViewModel;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationViewModel implements ViewModel<NotificationViewState> {

    NotificationViewState state;

    Task currentTaskForNotify;
    Set<TaskId> activeTaskNotifications;

    public NotificationViewModel() {
        state = NotificationViewState.none;
        activeTaskNotifications = new HashSet<>();
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

    public void resetState() {
        state = NotificationViewState.none;
    }

    @Override
    public void setState(@Nonnull NotificationViewState state) {
        this.state = state;
    }

    @Nonnull
    @Override
    public NotificationViewState getState() {
        return state;
    }
}
