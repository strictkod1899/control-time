package ru.strict.controltime.view.manager;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.view.boundary.ViewModel;

import javax.annotation.Nonnull;
import java.time.Duration;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerViewModel implements ViewModel<TimeManagerViewState> {

    TimeManagerViewState state;

    TimeManager actualTimeManager;
    Duration computerWorkDuration;

    public TimeManagerViewModel() {
        state = TimeManagerViewState.none;
    }
}
