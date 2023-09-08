package ru.strict.controltime.view.settings.main.event;

import lombok.RequiredArgsConstructor;
import ru.strict.event.Topic;

@RequiredArgsConstructor
public enum SettingsTopic implements Topic {
    showCreateTask("showCreateTask");

    final String topic;

    @Override
    public String getTopic() {
        return topic;
    }
}
