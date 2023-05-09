package ru.strict.controltime.initservice;

import ru.strict.controltime.timemanager.adapter.presenter.notification.NotificationPresenterImpl;
import ru.strict.controltime.timemanager.adapter.presenter.timemanager.TimeManagerPresenterImpl;
import ru.strict.ioc.annotation.Component;

public class PresentersInit {

    @Component
    public NotificationPresenterImpl notificationPresenter() {
        return NotificationPresenterImpl.init();
    }

    @Component
    public TimeManagerPresenterImpl timeManagerPresenter() {
        return TimeManagerPresenterImpl.init();
    }
}
