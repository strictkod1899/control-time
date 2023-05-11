package ru.strict.controltime.view.common;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

@UtilityClass
public class SwingUtil {

    public JPanel createImageButton(
            int hgap,
            int vgap,
            Color baseColor,
            Color selectColor,
            ImageIcon image,
            MouseListener... mouseListeners) {
        var layout = new FlowLayout(FlowLayout.CENTER, hgap, vgap);
        var panel = new JPanel();
        panel.setLayout(layout);
        panel.setBackground(baseColor);
        if (mouseListeners != null) {
            for (var mouseListener : mouseListeners) {
                panel.addMouseListener(mouseListener);
            }
        }

        panel.addMouseListener(new ChangeBackgroundMouseAction(panel, selectColor, baseColor));
        var label = new JLabel();
        label.addMouseListener(new ChangeBackgroundMouseAction(panel, selectColor, baseColor));
        if (image != null) {
            label.setIcon(image);
        }

        panel.add(label);
        return panel;
    }
}
