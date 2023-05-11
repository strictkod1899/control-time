package ru.strict.controltime.view.manager.component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.domain.entity.task.TaskId;
import ru.strict.controltime.timemanager.domain.entity.managetask.ManageTask;
import ru.strict.util.CommonUtil;
import ru.strict.view.swing.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskProgressBar extends JProgressBar {
    private static final Color notActiveProgressColor = new Color(219, 84, 97);

    final Color sourceProgressColor;
    @Getter
    final TaskId taskId;

    public TaskProgressBar(Task task) {
        sourceProgressColor = getForeground();
        this.taskId = task.getId();

        setMinimum(0);
        setMaximum((int)task.getSleepDuration().toMillis());
    }

    public void refreshAsNotActive() {
        setValue(0);
        setForeground(notActiveProgressColor);
    }

    public void refreshAsActive() {
        setValue(0);
        setForeground(sourceProgressColor);
    }

    public void refreshProgress(ManageTask manageTask) {
        var sleepDuration = manageTask.getTask().getSleepDuration();
        var lastStartedAt = manageTask.getLastProcessedAt().orElse(manageTask.getStartedAt());

        var expectedTaskFinishedAt = lastStartedAt.plus(sleepDuration.toDuration());

        var processedDurationMillis = Instant.now().toEpochMilli() - lastStartedAt.toEpochMilli();
        var remainingDurationMillis = expectedTaskFinishedAt.toEpochMilli() - Instant.now().toEpochMilli();
        var remainingPercent = CommonUtil.calcPercent(sleepDuration.toMillis(), remainingDurationMillis);

        setValue((int)processedDurationMillis);
        SwingUtil.refresh(this);
    }
}