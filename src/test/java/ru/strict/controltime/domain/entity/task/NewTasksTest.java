package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewTasksTest {

    @Test
    void testInit_ReturnEmptyTasks() {
        var tasks = Tasks.init();
        assertTrue(tasks.toCollection().isEmpty());
    }
}
