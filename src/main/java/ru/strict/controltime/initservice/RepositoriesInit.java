package ru.strict.controltime.initservice;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.strict.controltime.task.adapter.repository.task.TaskJsonRepository;
import ru.strict.controltime.timemanager.adapter.repository.manager.TimeManagerInMemoryRepository;
import ru.strict.env.EnvRegistry;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.ioc.annotation.Component;
import ru.strict.ioc.annotation.Configuration;

import java.io.File;

public class RepositoriesInit {

    @Component
    public String tasksFilePath(
            EnvRegistry envRegistry,
            @Component("appPath") String appPath) {
        var tasksFileName = envRegistry.getEnv(EnvRegistryInit.tasksFileNameKey);
        return appPath + File.separator + tasksFileName;
    }

    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class Start {
        TaskJsonRepository taskJsonRepository;

        @Configuration
        public void initTaskJsonRepository() {
            taskJsonRepository.init();
        }
    }
}
