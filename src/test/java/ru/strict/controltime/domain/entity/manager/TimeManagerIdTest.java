package ru.strict.controltime.domain.entity.manager;

import org.junit.jupiter.api.Test;
import ru.strict.domainprimitive.id.EntityIdError;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeManagerIdTest {

    @Test
    void testNewId_NoError() {
        var timeManagerId1 = TimeManagerId.init();
        var timeManagerId2 = TimeManagerId.init();

        assertNotEquals(timeManagerId1, timeManagerId2);
    }

    @Test
    void testIdFrom_StringIsNull_ThrowError() {
        try {
            TimeManagerId.from(null);
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.idIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testIdFrom_EmptyString_ThrowError() {
        try {
            TimeManagerId.from("");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.idIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testIdFrom_NotUUID_ThrowError() {
        try {
            TimeManagerId.from("123");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, EntityIdError.invalidIdFormatErrorCode));
            return;
        }

        throw new FailTestException();
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