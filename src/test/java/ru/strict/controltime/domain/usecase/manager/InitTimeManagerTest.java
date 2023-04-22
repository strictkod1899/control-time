package ru.strict.controltime.domain.usecase.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.strict.controltime.boundary.model.CreateTaskParams;
import ru.strict.controltime.boundary.usecase.TimeManagerUseCase;
import ru.strict.controltime.domain.entity.manager.TimeManager;
import ru.strict.controltime.domain.entity.task.TaskError;
import ru.strict.controltime.testdouble.stub.entity.TaskStub;
import ru.strict.exception.CodeableException;
import ru.strict.exception.Errors;
import ru.strict.test.AssertUtil;
import ru.strict.test.FailTestException;
import ru.strict.util.ReflectionUtil;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Disabled
class InitTimeManagerTest {

    TimeManagerUseCase timeManagerUseCase;

    @BeforeEach
    public void setUp() {
        timeManagerUseCase = new TimeManagerUseCaseImpl();
    }

    @Test
    void testInitTimeManager_ParamIsNull_ThrowException() {
        try {
            timeManagerUseCase.initTimeManager(null);
        } catch(CodeableException ex) {
            assertTrue(ex.equalsByCode(TimeManagerUseCaseError.createTaskParamsListIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testInitTimeManager_TaskParamsListIsEmpty_NoError() {
        timeManagerUseCase.initTimeManager(List.of());

        var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
        assertNotNull(timeManager);
        assertTrue(timeManager.getManageTasks().isEmpty());
    }

    @Test
    void testInitTimeManager_TaskParamsWithoutMessage_ThrowException() {
        var createTaskParams = CreateTaskParams.builder().
                sleepDuration(Duration.ofMinutes(123)).
                build();
        var createTasksParams = List.of(createTaskParams);

        try {
            timeManagerUseCase.initTimeManager(createTasksParams);
        } catch(CodeableException ex) {
            assertTrue(ex.equalsByCode(TaskError.messageIsEmptyErrorCode));

            var timeManager = ReflectionUtil.getFieldValue(timeManagerUseCase, "timeManager");
            assertNull(timeManager);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testInitTimeManager_TaskParamsWithoutSleepDuration_ThrowException() {
        var expectedErrorCodes = List.of(TaskError.sleepDurationIsRequiredErrorCode);

        var createTaskParams = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                build();
        var createTasksParams = List.of(createTaskParams);

        try {
            timeManagerUseCase.initTimeManager(createTasksParams);
        } catch(Errors.ErrorsException ex) {
            AssertUtil.assertExceptionByCodes(ex, expectedErrorCodes);

            var timeManager = ReflectionUtil.getFieldValue(timeManagerUseCase, "timeManager");
            assertNull(timeManager);
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testInitTimeManager_ValidParams_NoError() {
        var createTaskParams1 = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofMinutes(123)).
                build();
        var createTaskParams2 = CreateTaskParams.builder().
                message(TaskStub.getMessage().toString()).
                sleepDuration(Duration.ofMinutes(15)).
                build();
        var createTasksParams = List.of(createTaskParams1, createTaskParams2);

        timeManagerUseCase.initTimeManager(createTasksParams);

        var timeManager = ReflectionUtil.<TimeManager>getFieldValue(timeManagerUseCase, "timeManager");
        assertNotNull(timeManager);
        assertEquals(2, timeManager.getManageTasks().size());
    }

    @Test
    void testInitTimeManager_ManagerAlreadyInitialized_ThrowException() {

    }
}
