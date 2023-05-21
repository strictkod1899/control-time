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
public class AddTaskPanel extends JPanel {
    Params params;
    AddTaskWindow parentWindow;

    public AddTaskPanel(Params params, AddTaskWindow parentWindow) {
        this.params = params;
        this.parentWindow = parentWindow;

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

        var taskTextField = new JTextField();
        var textFieldDefaultSize = taskTextField.getPreferredSize();
        taskTextField.setPreferredSize(new Dimension(params.getMaxComponentWidth(), (int) textFieldDefaultSize.getHeight()));

        var timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        timePanel.setBackground(params.getBackground());

        var hoursTextField = new JTextField("00");
        hoursTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        var minutesTextField = new JTextField("00");
        minutesTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        var secondsTextField = new JTextField("00");
        secondsTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));

        var managePanel = new JPanel();
        managePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        managePanel.setBackground(params.getBackground());

        var createButton = new JButton("Создать");
        createButton.setFont(new Font(createButton.getFont().getName(), Font.BOLD, createButton.getFont().getSize()));

        var cancelButton = new JButton("Отмена");
        cancelButton.setFont(new Font(cancelButton.getFont().getName(), Font.BOLD, cancelButton.getFont().getSize()));
        cancelButton.addActionListener(e -> parentWindow.close());

        timePanel.add(hoursTextField);
        timePanel.add(new JLabel(" : "));
        timePanel.add(minutesTextField);
        timePanel.add(new JLabel(" : "));
        timePanel.add(secondsTextField);
        timePanel.add(new JLabel("    "));

        managePanel.add(createButton);
        managePanel.add(cancelButton);

        layout.setConstraints(taskTextField, layoutConstraints);
        add(taskTextField);
        layout.setConstraints(timePanel, layoutConstraints);
        add(timePanel);
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
