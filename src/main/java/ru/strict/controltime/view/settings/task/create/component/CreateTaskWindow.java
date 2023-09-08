package ru.strict.controltime.view.settings.task.create.component;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.common.BaseWindow;
import ru.strict.controltime.view.common.BaseWindowParams;
import ru.strict.controltime.view.common.TopPanelParamsBuilder;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskEvent;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskTopic;
import ru.strict.event.EventBroker;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTaskWindow extends BaseWindow {
    private static final int windowWidth = 250;
    private static final int windowHeight = 150;
    private static final Insets windowInsets = new Insets(2, 10, 2, 10);
    private static final int maxComponentWidth = windowWidth - windowInsets.left - windowInsets.right - 15;
    private static final Color windowBackgroundColor = new Color(255, 255, 255);

    final Params params;
    final EventBroker viewEventBroker;

    public CreateTaskWindow(Params params, EventBroker viewEventBroker) {
        super(getWindowParams());

        viewEventBroker.subscribe(CreateTaskTopic.cancel, this::onCancelCreateTask);
        viewEventBroker.subscribe(CreateTaskTopic.create, this::onCreateTask);

        this.params = params;
        this.viewEventBroker = viewEventBroker;
    }

    @Override
    protected void initFrame(@Nonnull JFrame frame) {
        super.initFrame(frame);

        frame.setLocation(params.locationX, params.locationY);
    }

    @Nonnull
    @Override
    protected JPanel createCenterPanel() {
        var centerPanel = new JPanel();
        centerPanel.setBackground(windowBackgroundColor);

        var taskPanelParams = CreateTaskPanel.Params.builder().
                background(getFrame().getBackground()).
                maxComponentWidth(maxComponentWidth).
                parentInsets(windowInsets).
                build();
        var emptyTaskPanel = new CreateTaskPanel(taskPanelParams, viewEventBroker);

        centerPanel.add(emptyTaskPanel);

        return centerPanel;
    }

    @Override
    protected void initComponents() {
    }

    @Override
    protected TopPanelParamsBuilder initTopPanelParams() {
        return super.initTopPanelParams().
                visibleExitButton(false).
                visibleTurnButton(false).
                visibleChangeSizeButton(false);
    }

    private void onCreateTask(Object event) {
        hide();
    }

    private void onCancelCreateTask(Object event) {
        hide();
    }

    private static BaseWindowParams getWindowParams() {
        return BaseWindowParams.builder().
                width(windowWidth).
                height(windowHeight).
                background(windowBackgroundColor).
                build();
    }

    @Value
    @Builder
    public static class Params {
        int locationX;
        int locationY;
    }
}
