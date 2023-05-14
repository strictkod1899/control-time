package ru.strict.controltime.view.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseWindow {
    final BaseWindowParams params;

    @Getter(value = AccessLevel.PROTECTED)
    JFrame frame;
    @Getter(value = AccessLevel.PROTECTED)
    JPanel centerPanel;

    public BaseWindow(@Nonnull BaseWindowParams params) {
        this.params = params;
        init();
    }

    public void show() {
        frame.setVisible(true);
        frame.setState(0);
    }

    public void hide() {
        frame.setVisible(false);
    }

    private void init() {
        frame = new JFrame();
        initFrame(frame);

        centerPanel = createCenterPanel();

        var topPanelParams = initTopPanelParams().build();
        var topPanel = new TopPanel(topPanelParams);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
    }

    protected void initFrame(@Nonnull JFrame frame) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        frame.setUndecorated(true);
        frame.setPreferredSize(new Dimension(params.getWidth(), params.getHeight()));
        frame.setSize(params.getWidth(), params.getHeight());
        frame.setBackground(params.getBackground());
        frame.getContentPane().setBackground(params.getBackground());

        params.getLogoFile().ifPresent(logoFile -> {
            frame.setIconImage((new ImageIcon(logoFile.getAbsolutePath())).getImage());
        });
    }

    protected TopPanelParamsBuilder initTopPanelParams() {
        var topPanelParamsBuilder = TopPanelParams.builder().
                parentWindow(frame).
                title(params.getTitle());
        params.getLogoFile().ifPresent(logoFile -> {
            topPanelParamsBuilder.iconPath(logoFile.getAbsolutePath());
        });

        return topPanelParamsBuilder;
    }

    @Nonnull
    protected abstract JPanel createCenterPanel();
}
