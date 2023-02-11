package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewTimeManagerTest {

    @Test
    void testNew_ReturnManager() {
        var timeManager = TimeManager.init();

        assertNotNull(timeManager.getTasks());
        assertTrue(timeManager.getTasks().isEmpty());
    }
}
