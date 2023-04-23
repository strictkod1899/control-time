package ru.strict.controltime.timemanager.domain.entity.manager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class TimeManagerIdTest {

    @Test
    void testNewId_NoError() {
        var timeManagerId1 = TimeManagerId.init();
        var timeManagerId2 = TimeManagerId.init();

        assertNotEquals(timeManagerId1, timeManagerId2);
    }

    @Test
    void testIdFrom_StringIsNull_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> TimeManagerId.from(null));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));
    }

    @Test
    void testIdFrom_EmptyString_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> TimeManagerId.from(""));

        assertTrue(actualEx.equalsByCode(EntityIdError.idIsEmptyErrorCode));
    }

    @Test
    void testIdFrom_NotUUID_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> TimeManagerId.from("123"));

        assertTrue(actualEx.equalsByCode(EntityIdError.invalidIdFormatErrorCode));
    }

    @Test
    void testIdFrom_ValidParam_NoError() {
        var expectedIdStr = UUID.randomUUID().toString();

        var timeManagerId1 = TimeManagerId.from(expectedIdStr);
        var timeManagerId2 = TimeManagerId.from(expectedIdStr);

        assertEquals(timeManagerId1, timeManagerId2);
        assertEquals(timeManagerId1.toString(), timeManagerId2.toString());
    }
}
