package ru.strict.controltime.view.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.view.boundary.BaseViewModel;

import javax.annotation.Nonnull;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SettingsViewModel extends BaseViewModel<SettingsViewState> {

    List<Task> existsTasks;

    public SettingsViewModel() {
        existsTasks = List.of();
    }

    @Nonnull
    @Override
    protected SettingsViewState getUnknownState() {
        return SettingsViewState.none;
    }
}
