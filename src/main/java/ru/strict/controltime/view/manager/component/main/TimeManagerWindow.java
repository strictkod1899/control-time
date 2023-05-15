package ru.strict.controltime.view.manager.component.main;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.controltime.view.common.BaseWindow;
import ru.strict.controltime.view.common.BaseWindowParams;
import ru.strict.controltime.view.common.TopPanelParamsBuilder;
import ru.strict.controltime.view.common.ViewUtil;
import ru.strict.controltime.view.manager.presenter.SettingsPresenter;
import ru.strict.util.TrayUtil;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerWindow extends BaseWindow {

    private static final int windowWidth = 250;
    private static final int windowHeight = 350;
    private static final Insets windowInsets = new Insets(2,10,2,10);
    private static final int maxComponentWidth = windowWidth - windowInsets.left - windowInsets.right - 15;
    private static final Color windowBackgroundColor = new Color(255, 255, 255);
    private static final String windowTitle = "control-time";

    final String appPath;
    final SettingsPresenter settingsPresenter;

    GridBagLayout centerPanelLayout;
    GridBagConstraints centerPanelLayoutConstraints;
    Map<TaskId, TaskProgressBar> taskProgressBarsMap;
    JLabel computerWorkDurationLabel;

    public TimeManagerWindow(String appPath, SettingsPresenter settingsPresenter) {
        super(getWindowParams(appPath));
        this.appPath = appPath;
        this.settingsPresenter = settingsPresenter;
        this.taskProgressBarsMap = new HashMap<>();
    }

    public void initTimeManager(TimeManager timeManager) {
        initComputerWorkDurationComponent();
        timeManager.getTasks().forEach(this::addTaskProgressBar);
    }

    public void refreshComputerWorkDuration(Duration computerWorkDuration) {
        var totalSeconds = computerWorkDuration.toSeconds();

        var hours = totalSeconds / TimeUnit.HOURS.toSeconds(1);
        var otherSeconds = totalSeconds - TimeUnit.HOURS.toSeconds(hours);

        var minutes = otherSeconds / TimeUnit.MINUTES.toSeconds(1);
        otherSeconds = otherSeconds - TimeUnit.MINUTES.toSeconds(minutes);

        var hoursStr = String.valueOf(hours).length() == 1 ? String.format("0%s", hours) : String.valueOf(hours);
        var minutesStr = String.valueOf(minutes).length() == 1 ? String.format("0%s", minutes) : String.valueOf(minutes);
        var secondsStr = String.valueOf(otherSeconds).length() == 1 ? String.format("0%s", otherSeconds) : String.valueOf(otherSeconds);

        computerWorkDurationLabel.setText(String.format("%s:%s:%s", hoursStr, minutesStr, secondsStr));
    }

    public void addOrRefreshTask(ManageTask manageTask) {
        if (taskProgressBarsMap.containsKey(manageTask.getTaskId())) {
            var taskProgressBar = taskProgressBarsMap.get(manageTask.getTaskId());
            taskProgressBar.refreshProgress(manageTask);
        } else {
            addTaskProgressBar(manageTask.getTask());
        }
    }

    private void onTrayDoubleClick(ActionEvent event) {
        show();
    }

    @Override
    protected void initFrame(@Nonnull JFrame frame) {
        super.initFrame(frame);

        frame.setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth(),
                Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight() - 40);

        var logoFile = ViewUtil.getLogoFile(appPath);
        TrayUtil.setTray("control-time", logoFile.getAbsolutePath(), this::onTrayDoubleClick, true);
    }

    @Override
    protected TopPanelParamsBuilder initTopPanelParams() {
        var settingsMenu = new JMenu("Настройки");
        var tasksMenuItem = new JMenuItem("Задачи");
        tasksMenuItem.addActionListener(e -> settingsPresenter.showTasksSettings());
        settingsMenu.add(tasksMenuItem);

        return super.initTopPanelParams().
                visibleChangeSizeButton(false).
                addMenu(settingsMenu);
    }

    private void initComputerWorkDurationComponent() {
        var labelCaption = new JLabel("Общее время работы ПК: ");
        computerWorkDurationLabel = new JLabel("00:00:00");

        var pcWorkingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pcWorkingPanel.setBackground(getFrame().getBackground());
        pcWorkingPanel.add(labelCaption);
        pcWorkingPanel.add(computerWorkDurationLabel);

        centerPanelLayout.setConstraints(pcWorkingPanel, centerPanelLayoutConstraints);
        getCenterPanel().add(pcWorkingPanel);
    }

    private void addTaskProgressBar(Task task) {
        var titleLabel = new JLabel(task.getMessage().toString());
        var taskProgressBar = new TaskProgressBar(task);
        taskProgressBar.setPreferredSize(new Dimension(maxComponentWidth,20));

        var taskPanel = new JPanel();
        taskPanel.setBackground(getFrame().getBackground());

        var taskLayout = new GridBagLayout();
        taskPanel.setLayout(taskLayout);

        var taskLayoutConstraints = new GridBagConstraints();
        taskLayoutConstraints.insets = new Insets(2,5,2,5);
        taskLayoutConstraints.gridy = GridBagConstraints.RELATIVE;
        taskLayoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        taskLayoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        taskLayoutConstraints.weightx = 1;
        taskLayoutConstraints.anchor = GridBagConstraints.NORTHWEST;

        taskLayout.setConstraints(titleLabel, taskLayoutConstraints);
        taskPanel.add(titleLabel);
        taskLayout.setConstraints(taskProgressBar, taskLayoutConstraints);
        taskPanel.add(taskProgressBar);

        centerPanelLayout.setConstraints(taskPanel, centerPanelLayoutConstraints);
        getCenterPanel().add(taskPanel);

        taskProgressBarsMap.put(task.getId(), taskProgressBar);
    }

    @Nonnull
    @Override
    protected JPanel createCenterPanel() {
        var centerPanel = new JPanel();
        centerPanel.setBackground(getFrame().getBackground());

        centerPanelLayout = new GridBagLayout();
        centerPanel.setLayout(centerPanelLayout);

        centerPanelLayoutConstraints = new GridBagConstraints();
        centerPanelLayoutConstraints.insets = windowInsets;
        centerPanelLayoutConstraints.gridx = 0;
        centerPanelLayoutConstraints.gridy = GridBagConstraints.RELATIVE;
        centerPanelLayoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        centerPanelLayoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        centerPanelLayoutConstraints.weightx = 1;
        centerPanelLayoutConstraints.anchor = GridBagConstraints.NORTH;

        var centerPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerPanelWrapper.setBackground(getFrame().getBackground());
        centerPanelWrapper.add(centerPanel);

        return centerPanelWrapper;
    }

    private static BaseWindowParams getWindowParams(String appPath) {
        var logoFile = ViewUtil.getLogoFile(appPath);

        return BaseWindowParams.builder().
                title(windowTitle).
                width(windowWidth).
                height(windowHeight).
                background(windowBackgroundColor).
                logoFile(logoFile).
                build();
    }
}
