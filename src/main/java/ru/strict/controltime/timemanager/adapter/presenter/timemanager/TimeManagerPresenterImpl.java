package ru.strict.controltime.timemanager.adapter.presenter.timemanager;

import ru.strict.controltime.timemanager.boundary.presenter.TimeManagerPresenter;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

public class TimeManagerPresenterImpl implements TimeManagerPresenter {

    private TimeManagerPresenterImpl() {}

    public static TimeManagerPresenterImpl init() {
        return new TimeManagerPresenterImpl();
    }

    @Override
    public void refreshTimeManager(TimeManager timeManager) {

    }
}
