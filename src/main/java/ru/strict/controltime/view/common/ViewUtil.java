package ru.strict.controltime.view.common;

import lombok.experimental.UtilityClass;
import ru.strict.util.ResourcesUtil;

import java.io.File;

@UtilityClass
public class ViewUtil {
    private static final String logoFileName = "logo.png";

    public static File getLogoFile(String appPath) {
        var logoFile = new File(appPath + File.separator + logoFileName);
        if (!logoFile.exists()) {
            ResourcesUtil.getResourceAsFile(logoFileName, logoFile.getAbsolutePath());
        }

        return logoFile;
    }
}
