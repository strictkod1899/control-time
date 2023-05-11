package ru.strict.controltime.view.manager.component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.domain.entity.manager.TimeManager;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.controltime.view.common.TopPanel;
import ru.strict.util.ResourcesUtil;
import ru.strict.util.TrayUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeManagerWindow {

    private static final int width = 250;
    private static final int height = 300;
    private static final Color backgroundColor = new Color(255, 255, 255);
    private static final String logoFileName = "logo.png";

    String appPath;

    JFrame frame;
    File logoFile;
    JPanel centerPanel;
    Map<TaskId, TaskProgressBar> taskProgressBarsMap;
    JLabel computerWorkDurationLabel;

    private TimeManagerWindow() {
    }

    public static TimeManagerWindow init(String appPath) {
        var window = new TimeManagerWindow();
        window.appPath = appPath;
        window.taskProgressBarsMap = new HashMap<>();

        return window;
    }

    public void init(TimeManager timeManager) {
        initLogoFile();
        initWindowParams();
        initTopPanel();
        initCenterPanel();
        initComputerWorkDurationComponent();
        initTaskProgressBars(timeManager);
    }

    public void show() {
        frame.setVisible(true);
        frame.setState(0);
    }

    public void hide() {
        frame.setVisible(false);
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

    private void initWindowParams() {
        frame = new JFrame();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        frame.setUndecorated(true);

        frame.setPreferredSize(new Dimension(width, height));
        frame.setSize(width, height);

        frame.setBackground(backgroundColor);
        frame.getContentPane().setBackground(backgroundColor);

        TrayUtil.setTray("control-time", logoFile.getAbsolutePath(), this::onTrayDoubleClick, true);
        frame.setIconImage((new ImageIcon(logoFile.getAbsolutePath())).getImage());

        frame.setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width - frame.getWidth(),
                Toolkit.getDefaultToolkit().getScreenSize().height - frame.getHeight() - 40);

    }

    private void initTopPanel() {
        var topPanel = TopPanel.builder().
                parentWindow(frame).
                visibleChangeSizeButton(false).
                iconPath(logoFile.getAbsolutePath()).
                title("control-time").
                build();

        frame.add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(frame.getBackground());
        centerPanel.setPreferredSize(frame.getPreferredSize());
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        frame.add(centerPanel, BorderLayout.CENTER);
    }

    private void initComputerWorkDurationComponent() {
        var labelCaption = new JLabel("Общее время работы ПК: ");
        computerWorkDurationLabel = new JLabel("00:00:00");

        var pcWorkingPanel = new JPanel();
        pcWorkingPanel.setBackground(frame.getBackground());
        pcWorkingPanel.add(labelCaption);
        pcWorkingPanel.add(computerWorkDurationLabel);

        centerPanel.add(pcWorkingPanel);
    }

    private void initTaskProgressBars(TimeManager timeManager) {
        timeManager.getTasks().forEach(this::addTaskProgressBar);
    }

    private void addTaskProgressBar(Task task) {
        var labelCaption = new JLabel(task.getMessage().toString());
        var taskProgressBar = new TaskProgressBar(task);

        var taskPanel = new JPanel();
        taskPanel.setBackground(frame.getBackground());
        taskPanel.add(labelCaption);
        taskPanel.add(taskProgressBar);

        centerPanel.add(taskPanel);
        taskProgressBarsMap.put(task.getId(), taskProgressBar);
    }

    private void initLogoFile() {
        var logoFile = new File(appPath + File.separator + logoFileName);
        if (!logoFile.exists()) {
            ResourcesUtil.getResourceAsFile(logoFileName, logoFile.getAbsolutePath());
        }

        this.logoFile = logoFile;
    }
}
