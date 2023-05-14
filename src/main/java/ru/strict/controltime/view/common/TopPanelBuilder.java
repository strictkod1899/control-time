package ru.strict.controltime.view.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.view.manager.main.TimeManagerViewComponentError;
import ru.strict.exception.Errors;
import ru.strict.validate.CommonValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopPanelBuilder {
    Container parentWindow;
    Boolean enableWindowMoving;
    Boolean visibleTurnButton;
    Boolean visibleChangeSizeButton;
    Boolean visibleExitButton;
    Integer hGap;
    Integer vGap;
    String title;
    Integer buttonSize;
    Integer iconSize;
    String iconPath;
    GridBagLayout layout;
    GridBagConstraints layoutConstraints;
    Color backgroundColor;
    List<JMenu> menuList;

    TopPanel topPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JMenuBar menuBar;

    Errors errors;

    public TopPanelBuilder() {
        errors = new Errors();
        menuList = new ArrayList<>();
    }

    public TopPanelBuilder parentWindow(Container parentWindow) {
        this.parentWindow = parentWindow;
        return this;
    }

    public TopPanelBuilder visibleChangeSizeButton(boolean visibleChangeSizeButton) {
        this.visibleChangeSizeButton = visibleChangeSizeButton;
        return this;
    }

    public TopPanelBuilder title(String title) {
        this.title = title;
        return this;
    }

    public TopPanelBuilder iconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public TopPanelBuilder addMenu(JMenu menu) {
        this.menuList.add(menu);
        return this;
    }

    public TopPanel build() {
        checkRequiredFields();
        if (errors.isPresent()) {
            throw errors.toException();
        }

        fillDefaultValues();

        initTopPanel();
        initComponents();
        initWindowMoving();

        return topPanel;
    }

    private void checkRequiredFields() {
        if (parentWindow == null) {
            errors.addError(TimeManagerViewComponentError.errParentWindowForTopPanelIsRequired());
        }
    }

    private void fillDefaultValues() {
        if (buttonSize == null) {
            buttonSize = 11;
        }
        if (iconSize == null) {
            iconSize = (int) ((double) this.buttonSize * 1.5D);
        }
        if (vGap == null) {
            vGap = 10;
        }
        if (hGap == null) {
            hGap = this.vGap * 2;
        }
        if (visibleTurnButton == null) {
            visibleTurnButton = true;
        }
        if (visibleChangeSizeButton == null) {
            visibleChangeSizeButton = true;
        }
        if (visibleExitButton == null) {
            visibleExitButton = true;
        }
        if (enableWindowMoving == null) {
            enableWindowMoving = true;
        }
        if (backgroundColor == null) {
            backgroundColor = ViewColor.FRAME_BACKGROUND.getColor();
        }
    }

    private void initTopPanel() {
        var topPanel = new TopPanel();
        topPanel.enableWindowMoving = enableWindowMoving;
        topPanel.visibleTurnButton = visibleTurnButton;
        topPanel.visibleChangeSizeButton = visibleChangeSizeButton;
        topPanel.visibleExitButton = visibleExitButton;
        topPanel.hGap = hGap;
        topPanel.vGap = vGap;
        topPanel.title = title;
        topPanel.buttonSize = buttonSize;
        topPanel.iconSize = iconSize;
        topPanel.iconPath = iconPath;
        topPanel.parentWindow = parentWindow;

        this.topPanel = topPanel;
    }

    private void initComponents() {
        layout = new GridBagLayout();
        layoutConstraints = new GridBagConstraints();
        topPanel.setLayout(layout);
        topPanel.setBackground(backgroundColor);

        layoutConstraints.gridx = 0;
        layoutConstraints.weightx = 1.0D;
        layoutConstraints.insets = new Insets(0, 0, 0, 0);
        layoutConstraints.fill = 2;
        layoutConstraints.anchor = 17;

        initLeftPanel();
        initRightPanel();

        layout.setConstraints(leftPanel, layoutConstraints);
        topPanel.add(leftPanel);
        layoutConstraints.gridx = 1;
        layoutConstraints.weightx = 0.0D;
        layout.setConstraints(rightPanel, layoutConstraints);
        topPanel.add(rightPanel);

        if (menuList.isEmpty()) {
            return;
        }

        initMenu();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridwidth = 0;
        layout.setConstraints(this.menuBar, layoutConstraints);
        topPanel.add(this.menuBar);
    }

    private void initLeftPanel() {
        var leftPanel = new JPanel();

        var layout = new GridBagLayout();
        var constraints = new GridBagConstraints();
        constraints.fill = 1;
        constraints.insets = new Insets(5, 8, 5, 8);
        constraints.weightx = 1.0D;
        leftPanel.setLayout(layout);
        leftPanel.setBackground(backgroundColor);

        var titleLabel = new JLabel(title);
        if (!CommonValidator.isNullOrEmpty(iconPath)) {
            titleLabel.setIcon(ImageUtil.resizeImage(iconPath, iconSize, iconSize));
        }
        titleLabel.setForeground(topPanel.getForeground());

        leftPanel.add(titleLabel);
        layout.setConstraints(titleLabel, constraints);

        this.leftPanel = leftPanel;
    }

    private void initRightPanel() {
        var exitButtonMouse = new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setButtonPressedValue(event, true);
                event.getComponent().setBackground(ViewColor.FOCUS_BACKGROUND.getColor());
            }

            public void mouseReleased(MouseEvent event) {
                setButtonPressedValue(event, false);
                event.getComponent().setBackground(topPanel.getBackground());
                System.exit(0);
            }
        };

        var turnButtonMouse = new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setButtonPressedValue(event, true);
                event.getComponent().setBackground(ViewColor.FOCUS_BACKGROUND.getColor());
            }

            public void mouseReleased(MouseEvent event) {
                setButtonPressedValue(event, false);
                event.getComponent().setBackground(topPanel.getBackground());

                var currentParent = topPanel.getParent();
                while(currentParent != null) {
                    if (currentParent instanceof JFrame) {
                        ((JFrame) currentParent).setState(1);
                        currentParent.setVisible(false);
                        break;
                    } else {
                        currentParent = currentParent.getParent();
                    }
                }
            }
        };

        var exitButton = SwingUtil.createImageButton(
                hGap,
                vGap,
                topPanel.getBackground(),
                ViewColor.PRESS_BACKGROUND.getColor(),
                Icon.CLOSE.getImageFromResources(buttonSize),
                exitButtonMouse
        );
        
        var turnButton = SwingUtil.createImageButton(
                hGap,
                vGap,
                topPanel.getBackground(),
                ViewColor.FOCUS_BACKGROUND.getColor(),
                Icon.TURN.getImageFromResources(buttonSize),
                turnButtonMouse
        );
        var changeSizeToSmallButton = SwingUtil.createImageButton(
                hGap,
                vGap,
                topPanel.getBackground(),
                ViewColor.FOCUS_BACKGROUND.getColor(),
                Icon.CHANGE_SIZE.getImageFromResources(buttonSize)
        );
        var changeSizeToFullButton = SwingUtil.createImageButton(
                hGap,
                vGap,
                topPanel.getBackground(),
                ViewColor.FOCUS_BACKGROUND.getColor(),
                Icon.CHANGE_SIZE_FULL.getImageFromResources(buttonSize)
        );


        changeSizeToSmallButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setButtonPressedValue(event, true);
                event.getComponent().setBackground(ViewColor.FOCUS_BACKGROUND.getColor());
            }

            public void mouseReleased(MouseEvent event) {
                setButtonPressedValue(event, false);
                event.getComponent().setBackground(topPanel.getBackground());
                topPanel.getParent()
                        .setSize((int) ((double) topPanel.getParent()
                                        .getParent()
                                        .getParent()
                                        .getParent()
                                        .getPreferredSize().width / 1.3D),
                                (int) ((double) topPanel.getParent()
                                        .getParent()
                                        .getParent()
                                        .getParent()
                                        .getPreferredSize().height / 1.3D));
                changeSizeToFullButton.setVisible(true);
                changeSizeToSmallButton.setVisible(false);
                topPanel.getParent().validate();
                topPanel.getParent().repaint();
            }
        });
        changeSizeToFullButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setButtonPressedValue(event, true);
                event.getComponent().setBackground(ViewColor.FOCUS_BACKGROUND.getColor());
            }

            public void mouseReleased(MouseEvent event) {
                setButtonPressedValue(event, false);
                event.getComponent().setBackground(topPanel.getBackground());
                topPanel.getParent()
                        .setSize(topPanel.getParent()
                                        .getParent()
                                        .getParent()
                                        .getParent()
                                        .getPreferredSize().width,
                                topPanel.getParent()
                                        .getParent()
                                        .getParent()
                                        .getParent()
                                        .getPreferredSize().height);
                topPanel.getParent().setLocation(0, 0);
                changeSizeToFullButton.setVisible(false);
                changeSizeToSmallButton.setVisible(true);
                topPanel.getParent().validate();
                topPanel.getParent().repaint();
            }
        });
        changeSizeToFullButton.setVisible(false);

        var activeButtons = new ArrayList<JPanel>(4);
        if (visibleTurnButton) {
            activeButtons.add(turnButton);
        }
        if (visibleChangeSizeButton) {
            activeButtons.add(changeSizeToSmallButton);
            activeButtons.add(changeSizeToFullButton);
        }
        if (visibleExitButton) {
            activeButtons.add(exitButton);
        }

        var rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(topPanel.getBackground());
        activeButtons.forEach(rightPanel::add);

        this.rightPanel = rightPanel;
    }

    private void initMenu() {
        this.menuBar = new JMenuBar();
        menuList.forEach(menuBar::add);
    }

    private void initWindowMoving() {
        if (!enableWindowMoving) {
            return;
        }

        var mouseListener = new TopPanel.MouseMotionMoved(parentWindow);
        addMouseListener(topPanel, mouseListener);
    }

    private void addMouseListener(Component component, TopPanel.MouseMotionMoved mouseListener) {
        component.addMouseMotionListener(mouseListener);
        component.addMouseListener(mouseListener);

        if (component instanceof JPanel panel) {
            Arrays.stream(panel.getComponents()).forEach(c -> {
                addMouseListener(c, mouseListener);
            });
        }
    }

    private void setButtonPressedValue(MouseEvent event, boolean value) {
        var listeners = ((JPanel) event.getSource()).getMouseListeners();

        for (var listener : listeners) {
            if (!(listener instanceof ChangeBackgroundMouseAction)) {
                continue;
            }

            ((ChangeBackgroundMouseAction) listener).setButPressed(value);
        };
    }
}
