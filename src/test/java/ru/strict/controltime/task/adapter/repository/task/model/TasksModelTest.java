package ru.strict.controltime.task.adapter.repository.task.model;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.task.testdouble.stub.entity.TaskStub;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TasksModelTest {

    @Test
    void testConvert_ValidTasks_NoError() {
        var expectedTasks = List.of(
                TaskStub.getTask(),
                TaskStub.getTask(),
                TaskStub.getTask()
        );

        var tasksModel = TasksModel.ToTasksModel(expectedTasks);
        var actualTasks = TasksModel.ToTaskEntities(tasksModel);

        assertEquals(expectedTasks, actualTasks);
    }
}
