package ru.strict.controltime.initservice;

import ru.strict.env.EnvRegistry;
import ru.strict.ioc.annotation.Component;

public class EnvRegistryInit {

    public static final String tasksFileNameKey = "TASKS_FILE_NAME";

    @Component
    public EnvRegistry envRegistry() {
        var envRegistry = new EnvRegistry();
        envRegistry.setEnv(tasksFileNameKey, "tasks.json");

        return envRegistry;
    }
}
