package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.strict.validate.CommonValidator;

import java.util.Objects;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Message {
    private static final int MESSAGE_MAX_LENGTH = 30;

    String value;

    public static Message from(String value) {
        return new Message(value);
    }

    private Message(String value) {
        if (CommonValidator.isNullOrEmpty(value)) {
            throw TaskError.errMessageIsEmpty();
        }
        if (value.length() > MESSAGE_MAX_LENGTH) {
            throw TaskError.errMessageTooLong();
        }
        
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
