package ru.strict.controltime.domain.entity.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.strict.exception.CodeableException;
import ru.strict.test.FailTestException;
import ru.strict.test.RandomUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
class MessageTest {

    @Test
    void testFrom_StringIsNull_ThrowError() {
        try {
            Message.from(null);
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.messageIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_StringIsEmpty_ThrowError() {
        try {
            Message.from("");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.messageIsEmptyErrorCode));
            return;
        }

        throw new FailTestException();
    }

    @Test
    void testFrom_StringIsTooLong_ThrowError() {
        try {
            Message.from("testmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessagetestmessage");
        } catch (CodeableException ex) {
            assertTrue(CodeableException.equalsByCode(ex, TaskError.messageTooLongErrorCode));
            return;
        }

        throw new FailTestException();
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
