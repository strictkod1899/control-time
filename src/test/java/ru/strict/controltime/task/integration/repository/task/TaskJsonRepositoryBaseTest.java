package ru.strict.controltime.task.integration.repository.task;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.strict.controltime.task.adapter.repository.task.TaskJsonRepository;
import ru.strict.controltime.task.domain.entity.task.Task;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;
import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.util.ClassUtil;
import ru.strict.util.FileUtil;

@FieldDefaults(level = AccessLevel.PACKAGE)
class TaskJsonRepositoryBaseTest {
    final String TASKS_FILE_NAME = "tasks.json";

    String tasksFilePath;
    TaskJsonRepository taskRepository;

    Task savedTask;

    @BeforeEach
    void setup() {
        tasksFilePath = ClassUtil.getFilePathByClass(TaskJsonRepositoryBaseTest.class, TASKS_FILE_NAME);

        taskRepository = new TaskJsonRepository(new JacksonObjectMapper(), tasksFilePath);
        taskRepository.init();

        prepareTestData();
    }

    @AfterEach
    void tearDownRepository() {
        FileUtil.removeIfExists(tasksFilePath);
    }

    private void prepareTestData() {
        savedTask = TaskStub.getTask();
        taskRepository.insert(savedTask);
    }
}
