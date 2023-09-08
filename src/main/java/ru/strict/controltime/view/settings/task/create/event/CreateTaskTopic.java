package ru.strict.controltime.view.settings.task.create.event;

import lombok.RequiredArgsConstructor;
import ru.strict.event.Topic;

@RequiredArgsConstructor
public enum CreateTaskTopic implements Topic {
    create("createTask"),
    cancel("cancelCreateTask");

    final String topic;

    @Override
    public String getTopic() {
        return topic;
    }
}
