package ru.strict.controltime.view.settings.component.tasks;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.view.common.BaseWindow;
import ru.strict.controltime.view.common.BaseWindowParams;
import ru.strict.controltime.view.common.TopPanelParamsBuilder;
import ru.strict.controltime.view.common.ViewUtil;
import ru.strict.ioc.annotation.Component;
import ru.strict.validate.CommonValidator;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TasksSettingsWindow extends BaseWindow {
    private static final int windowWidth = 250;
    private static final int windowHeight = 350;
    private static final Insets windowInsets = new Insets(2,10,2,10);
    private static final int maxComponentWidth = windowWidth - windowInsets.left - windowInsets.right - 15;
    private static final Color windowBackgroundColor = new Color(255, 255, 255);

    final List<Task> originalActualTasks;
    final SettingsListener settingsListener;

    JPanel centerPanel;
    GridBagLayout centerPanelLayout;
    GridBagConstraints centerPanelLayoutConstraints;

    public TasksSettingsWindow(
            @Component("appPath") String appPath,
            List<Task> actualTasks,
            SettingsListener settingsListener) {
        super(getWindowParams(appPath));

        CommonValidator.throwIfNull(actualTasks, "originalActualTasks");
        CommonValidator.throwIfNull(settingsListener, "settingsListener");

        this.originalActualTasks = actualTasks;
        this.settingsListener = settingsListener;

        init();
    }

    @Override
    protected void initFrame(@Nonnull JFrame frame) {
        super.initFrame(frame);

        frame.setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth() - 40,
                Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight() - 100);
    }

    @Nonnull
    @Override
    protected JPanel createCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(getFrame().getBackground());

        centerPanelLayout = new GridBagLayout();
        centerPanel.setLayout(centerPanelLayout);

        centerPanelLayoutConstraints = new GridBagConstraints();
        centerPanelLayoutConstraints.insets = windowInsets;
        centerPanelLayoutConstraints.gridx = 0;
        centerPanelLayoutConstraints.gridy = GridBagConstraints.RELATIVE;
        centerPanelLayoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        centerPanelLayoutConstraints.anchor = GridBagConstraints.NORTHWEST;

        var centerPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerPanelWrapper.setBackground(getFrame().getBackground());
        centerPanelWrapper.add(centerPanel);

        return centerPanelWrapper;
    }

    @Override
    protected void initComponents() {
        originalActualTasks.forEach(this::addTaskToSettings);

        var footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        var addTaskButton = new JButton("Добавить напоминание");
        addTaskButton.addActionListener(this::showAddTaskWindow);

        footerPanel.add(addTaskButton);

        centerPanelLayout.setConstraints(footerPanel, centerPanelLayoutConstraints);
        centerPanel.add(footerPanel);
        centerPanelLayout.setConstraints(addTaskButton, centerPanelLayoutConstraints);
        centerPanel.add(addTaskButton);
    }

    @Override
    protected TopPanelParamsBuilder initTopPanelParams() {
        return super.initTopPanelParams().
                visibleTurnButton(false).
                visibleChangeSizeButton(false).
                visibleExitButton(true).
                exitButtonAction(this::close);
    }

    private void showAddTaskWindow(ActionEvent event) {
        var sourceButton = (JButton)event.getSource();
        var sourceLocation = sourceButton.getLocationOnScreen();

        var params = AddTaskWindow.Params.builder().
                locationX((int)sourceLocation.getX() - (windowWidth / 2)).
                locationY((int)sourceLocation.getY() - 50).
                build();

        var addTaskWindow = new AddTaskWindow(params);
        addTaskWindow.init();
        addTaskWindow.show();
    }

    private void addTaskToSettings(Task task) {
        var taskPanelParams = TaskPanel.Params.builder().
                background(getFrame().getBackground()).
                maxComponentWidth(maxComponentWidth).
                parentInsets(windowInsets).
                build();
        var taskPanel = new TaskPanel(task, taskPanelParams);

        centerPanelLayout.setConstraints(taskPanel, centerPanelLayoutConstraints);
        centerPanel.add(taskPanel);
    }

    private static BaseWindowParams getWindowParams(String appPath) {
        var logoFile = ViewUtil.getLogoFile(appPath);

        return BaseWindowParams.builder().
                width(windowWidth).
                height(windowHeight).
                background(windowBackgroundColor).
                logoFile(logoFile).
                build();
    }
}
