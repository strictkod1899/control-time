package ru.strict.controltime;

import lombok.extern.slf4j.Slf4j;
import ru.strict.controltime.initservice.IoC;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class Main {

    public static void main(String[] args) {
        try {
            initApp();
        } catch (Exception ex) {
            logError(ex);
        }
    }

    private static void initApp() {
        IoC.instance();
    }

    private static void logError(Exception ex) {
        log.error("Main exception", ex);

        JOptionPane.showMessageDialog((Component)null, ex.toString(), "Error", 0);
        System.err.println(String.format("ERROR:\n%s\n%s", ex, Arrays.toString(ex.getStackTrace())));
        try {
            System.in.read();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
