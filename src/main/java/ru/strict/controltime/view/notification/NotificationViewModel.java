package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.view.boundary.ViewModel;

import javax.annotation.Nonnull;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationViewModel implements ViewModel<NotificationViewState> {

    NotificationViewState state;

    Task currentTaskForNotify;

    public NotificationViewModel() {
        state = NotificationViewState.none;
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
