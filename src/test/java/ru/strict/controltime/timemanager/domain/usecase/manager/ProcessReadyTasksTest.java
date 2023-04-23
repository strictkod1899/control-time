package ru.strict.controltime.timemanager.domain.usecase.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Disabled
class ProcessReadyTasksTest extends TimeManagerUseCaseCommon {

    @BeforeEach
    public void setupTest() {
        setUpUseCase();
    }

    @Test
    void testProcess_GetTimeManagerRepoError_ThrowException() {
        timeManagerUseCase.processReadyTasks();
    }

    @Test
    void testProcess_TimeManagerNotFound_ThrowException() {
        assertTrue(false, "impl me");
    }

    @Test
    void testProcess_EmptyTasks_NoError() {
        assertTrue(false, "impl me");
    }

    @Test
    void testProcess_WithoutReadyTasks_NoError() {
        assertTrue(false, "impl me");
    }

    @Test
    void testProcess_WithReadyTasks_PresenterError_ThrowException() {
        assertTrue(false, "impl me");
    }

    @Test
    void testProcess_WithReadyTasks_ValidProcess_NoError() {
        assertTrue(false, "impl me");
    }
}
