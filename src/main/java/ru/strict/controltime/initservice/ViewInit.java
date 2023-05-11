package ru.strict.controltime.initservice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.adapter.controller.inner.manager.TimeManagerInnerController;
import ru.strict.controltime.view.manager.TimeManagerViewController;
import ru.strict.ioc.annotation.Component;
import ru.strict.ioc.annotation.Configuration;

public class ViewInit {

    @Component
    public TimeManagerViewController timeManagerViewController(
            @Component("appPath") String appPath) {
        return TimeManagerViewController.init(appPath);
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Start {
        TimeManagerInnerController timeManagerInnerController;

        @Configuration
        public void showTimeManagerView() {
            timeManagerInnerController.initTimeManager();
        }
    }
}
