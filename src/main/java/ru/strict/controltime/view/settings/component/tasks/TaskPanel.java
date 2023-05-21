package ru.strict.controltime.view.settings.component.tasks;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.domain.entity.task.Task;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskPanel extends JPanel {
    Task task;
    Params params;

    public TaskPanel(Task task, Params params) {
        this.task = task;
        this.params = params;

        initComponents();
    }

    private void initComponents() {
        setBackground(params.getBackground());

        var layout = new GridBagLayout();
        setLayout(layout);

        var layoutConstraints = new GridBagConstraints();
        layoutConstraints.insets = new Insets(2, 0, 2, 0);
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = GridBagConstraints.RELATIVE;
        layoutConstraints.gridwidth = GridBagConstraints.REMAINDER;
        layoutConstraints.fill = GridBagConstraints.HORIZONTAL;
        layoutConstraints.weightx = 1;
        layoutConstraints.anchor = GridBagConstraints.NORTH;

        var taskTextField = new JTextField(task.getMessage().toString());
        var textFieldDefaultSize = taskTextField.getPreferredSize();
        taskTextField.setPreferredSize(new Dimension(params.getMaxComponentWidth(), (int) textFieldDefaultSize.getHeight()));

        var managePanel = new JPanel();
        managePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        managePanel.setBackground(params.getBackground());

        var totalSeconds = task.getSleepDuration().toDuration().toSeconds();

        var hours = totalSeconds / TimeUnit.HOURS.toSeconds(1);
        var otherSeconds = totalSeconds - TimeUnit.HOURS.toSeconds(hours);

        var minutes = otherSeconds / TimeUnit.MINUTES.toSeconds(1);
        otherSeconds = otherSeconds - TimeUnit.MINUTES.toSeconds(minutes);

        var hoursStr = String.valueOf(hours).length() == 1 ? String.format("0%s", hours) : String.valueOf(hours);
        var minutesStr = String.valueOf(minutes).length() == 1 ? String.format("0%s", minutes) : String.valueOf(minutes);
        var secondsStr = String.valueOf(otherSeconds).length() == 1 ? String.format("0%s", otherSeconds) : String.valueOf(otherSeconds);

        var hoursTextField = new JTextField(hoursStr);
        hoursTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        var minutesTextField = new JTextField(minutesStr);
        minutesTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        var secondsTextField = new JTextField(secondsStr);
        secondsTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));

        var deleteButton = new JButton("Удалить");
        deleteButton.setFont(new Font(deleteButton.getFont().getName(), Font.BOLD, deleteButton.getFont().getSize()));
        deleteButton.setForeground(Color.RED);

        managePanel.add(hoursTextField);
        managePanel.add(new JLabel(" : "));
        managePanel.add(minutesTextField);
        managePanel.add(new JLabel(" : "));
        managePanel.add(secondsTextField);
        managePanel.add(new JLabel("    "));
        managePanel.add(deleteButton);

        layout.setConstraints(taskTextField, layoutConstraints);
        add(taskTextField);
        layout.setConstraints(managePanel, layoutConstraints);
        add(managePanel);
    }

    @Value
    @Builder
    public static class Params {
        Color background;
        int maxComponentWidth;
        Insets parentInsets;
    }
}
