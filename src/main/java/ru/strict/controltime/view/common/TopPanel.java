package ru.strict.controltime.view.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TopPanel extends JPanel {
    boolean enableWindowMoving;
    boolean visibleTurnButton;
    boolean visibleChangeSizeButton;
    boolean visibleExitButton;
    int hGap;
    int vGap;
    String title;
    int buttonSize;
    int iconSize;
    String iconPath;
    Container parentWindow;

    public static TopPanelBuilder builder() {
        return new TopPanelBuilder();
    }

    public static class MouseMotionMoved extends MouseAdapter implements MouseMotionListener {
        private final Container parent;
        private Point position;

        public MouseMotionMoved(Container parent) {
            this.parent = parent;
        }

        public void mouseDragged(MouseEvent event) {
            if (parent.getWidth() == Toolkit.getDefaultToolkit().getScreenSize().width ||
                    parent.getHeight() == Toolkit.getDefaultToolkit().getScreenSize().height) {
                return;
            }

            int thisX = parent.getLocation().x;
            int thisY = parent.getLocation().y;
            int xMoved = thisX + event.getX() - (thisX + position.x);
            int yMoved = thisY + event.getY() - (thisY + position.y);
            int X = thisX + xMoved;
            int Y = thisY + yMoved;

            parent.setLocation(X, Y);
        }

        public void mousePressed(MouseEvent event) {
            position = event.getPoint();
        }
    }
}
