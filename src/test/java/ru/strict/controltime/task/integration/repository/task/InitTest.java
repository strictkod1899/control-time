package ru.strict.controltime.task.integration.repository.task;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.adapter.repository.task.TaskJsonRepository;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.util.ClassUtil;
import ru.strict.util.FileUtil;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class InitTest {

    @Test
    void testInit_ValidFilePath_FileCreated() {
        var tasksFilePathStr = ClassUtil.getFilePathByClass(TaskJsonRepositoryBaseTest.class, "init_tasks_test.json");

        var tasksFilePath = Path.of(tasksFilePathStr);
        assertFalse(Files.exists(tasksFilePath));

        var taskRepository = new TaskJsonRepository(new JacksonObjectMapper(), tasksFilePathStr);
        taskRepository.init();

        assertTrue(Files.exists(tasksFilePath));
        FileUtil.removeIfExists(tasksFilePathStr);
    }
}
