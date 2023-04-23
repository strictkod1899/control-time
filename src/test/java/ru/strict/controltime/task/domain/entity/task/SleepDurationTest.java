package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.exception.CodeableException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class SleepDurationTest {

    @Test
    void testFrom_DurationIsNull_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> SleepDuration.from(null));

        assertTrue(actualEx.equalsByCode(TaskError.durationIsRequiredErrorCode));
    }

    @Test
    void testFrom_DurationIsZero_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> SleepDuration.from(Duration.ZERO));

        assertTrue(actualEx.equalsByCode(TaskError.sleepDurationIsTooSmallErrorCode));
    }

    @Test
    void testFrom_DurationIsSmall_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> SleepDuration.from(Duration.ofSeconds(10)));

        assertTrue(actualEx.equalsByCode(TaskError.sleepDurationIsTooSmallErrorCode));
    }

    @Test
    void tesFrom_ValidParam_NoError() {
        var expectedDuration = Duration.ofSeconds(50);

        var sleepDuration = SleepDuration.from(expectedDuration);

        assertEquals(expectedDuration.toNanos(), sleepDuration.toNanos());
    }
}
