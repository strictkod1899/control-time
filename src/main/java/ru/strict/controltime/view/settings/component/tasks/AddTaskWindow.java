package ru.strict.controltime.view.settings.component.tasks;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.common.BaseWindow;
import ru.strict.controltime.view.common.BaseWindowParams;
import ru.strict.controltime.view.common.TopPanelParamsBuilder;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddTaskWindow extends BaseWindow {

    private static final int windowWidth = 250;
    private static final int windowHeight = 150;
    private static final Insets windowInsets = new Insets(2,10,2,10);
    private static final int maxComponentWidth = windowWidth - windowInsets.left - windowInsets.right - 15;
    private static final Color windowBackgroundColor = new Color(255, 255, 255);

    final Params params;
    final CreateTaskListener createTaskListener;

    public AddTaskWindow(Params params, CreateTaskListener createTaskListener) {
        super(getWindowParams());

        this.params = params;
        this.createTaskListener = createTaskListener;
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

        var taskPanelParams = AddTaskPanel.Params.builder().
                background(getFrame().getBackground()).
                maxComponentWidth(maxComponentWidth).
                parentInsets(windowInsets).
                build();
        var emptyTaskPanel = new AddTaskPanel(taskPanelParams, this, createTaskListener);

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
