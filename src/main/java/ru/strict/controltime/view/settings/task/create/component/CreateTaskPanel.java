package ru.strict.controltime.view.settings.task.create.component;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskTopic;
import ru.strict.controltime.view.settings.task.create.event.CreateTaskEvent;
import ru.strict.event.EventBroker;
import ru.strict.validate.CommonValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTaskPanel extends JPanel {
    final Params params;
    final EventBroker viewEventBroker;

    JTextField taskTextField;
    JTextField hoursTextField;
    JTextField minutesTextField;
    JTextField secondsTextField;

    public CreateTaskPanel(Params params, EventBroker viewEventBroker) {
        this.params = params;
        this.viewEventBroker = viewEventBroker;

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

        taskTextField = new JTextField();
        var textFieldDefaultSize = taskTextField.getPreferredSize();
        taskTextField.setPreferredSize(new Dimension(params.getMaxComponentWidth(), (int) textFieldDefaultSize.getHeight()));

        var timePanel = new JPanel();
        timePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        timePanel.setBackground(params.getBackground());

        hoursTextField = new JTextField("00");
        hoursTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        minutesTextField = new JTextField("00");
        minutesTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));
        secondsTextField = new JTextField("00");
        secondsTextField.setPreferredSize(new Dimension(25, (int) textFieldDefaultSize.getHeight()));

        var managePanel = new JPanel();
        managePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        managePanel.setBackground(params.getBackground());

        var createButton = new JButton("Создать");
        createButton.setFont(new Font(createButton.getFont().getName(), Font.BOLD, createButton.getFont().getSize()));
        createButton.addActionListener(this::onCreateTask);

        var cancelButton = new JButton("Отмена");
        cancelButton.setFont(new Font(cancelButton.getFont().getName(), Font.BOLD, cancelButton.getFont().getSize()));
        cancelButton.addActionListener(e -> viewEventBroker.sendEvent(CreateTaskTopic.cancel, new Object()));

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

    private void onCreateTask(ActionEvent event) {
        var message = taskTextField.getText();

        var hoursStr = hoursTextField.getText();
        var minutesStr = minutesTextField.getText();
        var secondsStr = secondsTextField.getText();

        var totalDuration = Duration.ZERO;
        if (CommonValidator.isInteger(hoursStr)) {
            var hours = Integer.parseInt(hoursStr);
            totalDuration = totalDuration.plusHours(hours);
        }
        if (CommonValidator.isInteger(minutesStr)) {
            var minutes = Integer.parseInt(minutesStr);
            totalDuration = totalDuration.plusMinutes(minutes);
        }
        if (CommonValidator.isInteger(secondsStr)) {
            var seconds = Integer.parseInt(secondsStr);
            totalDuration = totalDuration.plusSeconds(seconds);
        }

        var viewEvent = CreateTaskEvent.builder().
                message(message).
                duration(totalDuration).
                build();
        viewEventBroker.sendEvent(CreateTaskTopic.create, viewEvent);
    }

    @Value
    @Builder
    public static class Params {
        Color background;
        int maxComponentWidth;
        Insets parentInsets;
    }
}
