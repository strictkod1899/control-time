package ru.strict.controltime.timemanager.adapter.presenter.timemanager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.view.manager.TimeManagerViewController;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerPresenterImpl implements TimeManagerPresenter {

    TimeManagerViewController timeManagerViewController;

    private TimeManagerPresenterImpl() {}

    public static TimeManagerPresenterImpl from(
            TimeManagerViewController timeManagerViewController
    ) {
        var timeManagerPresenter = new TimeManagerPresenterImpl();
        timeManagerPresenter.timeManagerViewController = timeManagerViewController;

        return timeManagerPresenter;
    }

    @Override
    public void refreshTimeManager(TimeManager timeManager) {
        timeManagerViewController.refreshTimeManager(timeManager);
    }

    @Override
    public void showTimeManager(TimeManager timeManager) {
        timeManagerViewController.showTimeManager(timeManager);
    }
}
