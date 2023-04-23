package ru.strict.controltime.task.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.exception.CodeableException;
import ru.strict.test.RandomUtil;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
class MessageTest {

    @Test
    void testFrom_StringIsNull_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> Message.from(null));

        assertTrue(actualEx.equalsByCode(TaskError.messageIsEmptyErrorCode));
    }

    @Test
    void testFrom_StringIsEmpty_ThrowError() {
        var actualEx = assertThrows(CodeableException.class, () -> Message.from(""));

        assertTrue(actualEx.equalsByCode(TaskError.messageIsEmptyErrorCode));
    }

    @Test
    void testFrom_StringIsTooLong_ThrowError() {
        var longMessage = RandomUtil.generateStr(Message.MAX_MESSAGE_LENGTH+1);

        var actualEx = assertThrows(CodeableException.class, () -> Message.from(longMessage));

        assertTrue(actualEx.equalsByCode(TaskError.messageIsTooLongErrorCode));
    }

    @Test
    void tesFrom_ValidParam_NoError() {
        var expectedMessageStr = RandomUtil.generateDefaultStr();

        var message1 = Message.from(expectedMessageStr);
        var message2 = Message.from(expectedMessageStr);

        assertEquals(message1, message2);
        assertEquals(message1.toString(), message2.toString());
    }
}
