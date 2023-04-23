package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;
import ru.strict.test.RandomUtil;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class SleepDurationTest {

    @Test
    void testFrom_DurationIsNull_ThrowError() {
        try {
            SleepDuration.from(null);
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.durationIsRequiredErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_DurationIsZero_ThrowError() {
        try {
            SleepDuration.from(Duration.ZERO);
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.sleepDurationIsTooSmallErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_DurationIsSmall_ThrowError() {
        try {
            SleepDuration.from(Duration.ofSeconds(10));
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.sleepDurationIsTooSmallErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void tesFrom_ValidParam_NoError() {
        var expectedDuration = Duration.ofSeconds(50);

        var sleepDuration = SleepDuration.from(expectedDuration);

        assertEquals(expectedDuration.toNanos(), sleepDuration.toNanos());
    }
}
