package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.View;
import ru.strict.view.swing.NotificationWindow;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationView implements View<NotificationViewState, NotificationViewModel> {
    NotificationViewModel model;

    public NotificationView(NotificationViewModel model) {
        CommonValidator.throwIfNull(model, "notificationViewModel");

        this.model = model;
    }

    @Override
    public synchronized void refresh(@Nonnull NotificationViewState state) {
        model.setState(state);
        refresh();
    }

    @Override
    public synchronized void refresh() {
        switch (model.getState()) {
            case showNotification:
                showNotification();
                break;
            case none:
                break;
            default:
                throw NotificationViewError.errUnsupportedViewState(model.getState());
        }
    }

    private void showNotification() {
        CommonValidator.throwIfNull(model.getCurrentTaskForNotify(), "taskForNotify");

        var task = model.getCurrentTaskForNotify();
        var notificationWindow = new NotificationWindow(task.getMessage().toString());
        notificationWindow.addCustomCloseButtonAction((e) -> {
            model.removeActiveTaskNotification(task.getId());
        });
        notificationWindow.show();

        model.addActiveTaskNotification(task.getId());
        model.setCurrentTaskForNotify(null);
        model.resetState();
    }

    @Nonnull
    @Override
    public NotificationViewModel getModel() {
        return model;
    }
}
