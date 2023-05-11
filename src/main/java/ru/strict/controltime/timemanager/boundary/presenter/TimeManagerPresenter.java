package ru.strict.controltime.timemanager.boundary.presenter;

import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;

public interface TimeManagerPresenter {
    void showTimeManager(TimeManager timeManager);
    void refreshTimeManager(TimeManager timeManager);
}
