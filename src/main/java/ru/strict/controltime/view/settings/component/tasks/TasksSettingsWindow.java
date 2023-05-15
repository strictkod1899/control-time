package ru.strict.controltime.view.settings.component.tasks;

import ru.strict.controltime.view.common.BaseWindow;
import ru.strict.controltime.view.common.BaseWindowParams;

import javax.annotation.Nonnull;
import javax.swing.*;

public class TasksSettingsWindow extends BaseWindow {

    public TasksSettingsWindow() {
        super(getWindowParams());
    }

    @Nonnull
    @Override
    protected JPanel createCenterPanel() {
        return new JPanel();
    }

    private static BaseWindowParams getWindowParams() {
        return BaseWindowParams.builder().build();
    }
}
