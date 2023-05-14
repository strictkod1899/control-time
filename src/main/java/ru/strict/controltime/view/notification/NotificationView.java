package ru.strict.controltime.view.notification;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.BaseView;
import ru.strict.view.swing.NotificationWindow;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationView extends BaseView<NotificationViewState, NotificationViewModel> {
    public NotificationView(@Nonnull NotificationViewModel model) {
        super(model);
    }

    @Override
    public synchronized void refresh() {
        switch (getModel().getState()) {
            case showNotification:
                showNotification();
                break;
            case none:
                break;
            default:
                throw NotificationViewError.errUnsupportedViewState(getModel().getState());
        }
    }

    @Nonnull
    @Override
    protected Object getLockObject(@Nonnull NotificationViewState state) {
        return this;
    }

    private void showNotification() {
        CommonValidator.throwIfNull(getModel().getCurrentTaskForNotify(), "taskForNotify");

        var task = getModel().getCurrentTaskForNotify();
        var notificationWindow = new NotificationWindow(task.getMessage().toString());
        notificationWindow.getParams().addCustomCloseButtonAction((e) -> {
            getModel().removeActiveTaskNotification(task.getId());
        });
        notificationWindow.show();

        getModel().addActiveTaskNotification(task.getId());
        getModel().setCurrentTaskForNotify(null);
    }
}
