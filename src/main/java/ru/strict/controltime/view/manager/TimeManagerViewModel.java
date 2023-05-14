package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.view.boundary.BaseViewModel;
import ru.strict.view.boundary.ViewModel;

import javax.annotation.Nonnull;
import java.time.Duration;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerViewModel extends BaseViewModel<TimeManagerViewState> {
    TimeManager actualTimeManager;
    Duration computerWorkDuration;

    @Override
    protected @Nonnull TimeManagerViewState getUnknownState() {
        return TimeManagerViewState.none;
    }
}
