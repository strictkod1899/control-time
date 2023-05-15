package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.manager.component.main.TimeManagerWindow;
import ru.strict.controltime.view.manager.presenter.SettingsPresenter;
import ru.strict.controltime.view.settings.SettingsViewController;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.BaseView;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerView extends BaseView<TimeManagerViewState, TimeManagerViewModel> {
    TimeManagerWindow window;

    public TimeManagerView(
            @Nonnull TimeManagerViewModel model,
            String appPath,
            SettingsPresenter settingsPresenter) {
        super(model);
        this.window = new TimeManagerWindow(appPath, settingsPresenter);
    }

    @Override
    protected void refresh() {
        switch (getModel().getState()) {
            case init:
                initActualTimeManager();
                break;
            case refreshTimeManager:
                refreshActualTimeManager();
                break;
            case refreshComputerWorkDuration:
                refreshComputerWorkDuration();
                break;
            case none:
                break;
            default:
                throw TimeManagerViewError.errUnsupportedViewState(getModel().getState());
        }
    }

    @Nonnull
    @Override
    protected Object getLockObject(@Nonnull TimeManagerViewState state) {
        return this;
    }

    private void initActualTimeManager() {
        CommonValidator.throwIfNull(getModel().getActualTimeManager(), "actualTimeManager");

        window.initTimeManager(getModel().getActualTimeManager());
        window.show();
    }

    private void refreshActualTimeManager() {
        CommonValidator.throwIfNull(getModel().getActualTimeManager(), "actualTimeManager");

        getModel().getActualTimeManager().
                getManageTasks().
                forEach(window::addOrRefreshTask);
    }

    private void refreshComputerWorkDuration() {
        CommonValidator.throwIfNull(getModel().getComputerWorkDuration(), "computerWorkDuration");

        window.refreshComputerWorkDuration(getModel().getComputerWorkDuration());
    }
}
