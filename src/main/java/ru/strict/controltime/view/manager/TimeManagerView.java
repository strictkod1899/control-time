package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.manager.main.TimeManagerWindow;
import ru.strict.validate.CommonValidator;
import ru.strict.view.boundary.View;

import javax.annotation.Nonnull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeManagerView implements View<TimeManagerViewState, TimeManagerViewModel> {

    TimeManagerViewModel model;
    TimeManagerWindow window;

    public TimeManagerView(TimeManagerViewModel model, String appPath) {
        this.model = model;
        this.window = new TimeManagerWindow(appPath);
    }

    @Override
    public synchronized void refresh(@Nonnull TimeManagerViewState state) {
        model.setState(state);
        refresh();
    }

    @Override
    public synchronized void refresh() {
        switch (model.getState()) {
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
                throw TimeManagerViewError.errUnsupportedViewState(model.getState());
        }
    }

    @Nonnull
    @Override
    public TimeManagerViewModel getModel() {
        return model;
    }

    private void initActualTimeManager() {
        CommonValidator.throwIfNull(model.getActualTimeManager(), "actualTimeManager");

        window.init(model.getActualTimeManager());
        window.show();
    }

    private void refreshActualTimeManager() {
        CommonValidator.throwIfNull(model.getActualTimeManager(), "actualTimeManager");

        model.getActualTimeManager().
                getManageTasks().
                forEach(window::addOrRefreshTask);
    }

    private void refreshComputerWorkDuration() {
        CommonValidator.throwIfNull(model.getComputerWorkDuration(), "computerWorkDuration");

        window.refreshComputerWorkDuration(model.getComputerWorkDuration());
    }
}
