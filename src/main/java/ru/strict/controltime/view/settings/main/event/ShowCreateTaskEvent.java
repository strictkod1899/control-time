package ru.strict.controltime.view.settings.main.event;

import lombok.Builder;
import lombok.Value;
import ru.strict.controltime.view.settings.task.create.component.CreateTaskWindow;

@Value
@Builder
public class ShowCreateTaskEvent {
    int windowLocationX;
    int windowLocationY;
}
