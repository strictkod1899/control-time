package ru.strict.controltime.initservice;

import ru.strict.controltime.task.adapter.repository.task.TaskJsonRepository;
import ru.strict.controltime.timemanager.adapter.repository.manager.TimeManagerInMemoryRepository;
import ru.strict.env.EnvRegistry;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.ioc.annotation.Component;

import java.io.File;

public class RepositoriesInit {

    @Component
    public TaskJsonRepository taskJsonRepository(
            EnvRegistry envRegistry,
            JacksonObjectMapper jacksonObjectMapper,
            @Component("appPath") String appPath) {
        var tasksFileName = envRegistry.getEnv(EnvRegistryInit.tasksFileNameKey);
        var tasksFilePath = appPath + File.separator + tasksFileName;

        return TaskJsonRepository.builder().
                jacksonObjectMapper(jacksonObjectMapper).
                filePath(tasksFilePath).
                build();
    }

    @Component
    public TimeManagerInMemoryRepository timeManagerRepository() {
        return TimeManagerInMemoryRepository.init();
    }
}
