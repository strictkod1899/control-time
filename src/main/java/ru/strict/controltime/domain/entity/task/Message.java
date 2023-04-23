package ru.strict.controltime.domain.entity.task;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import ru.strict.validate.CommonValidator;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Message {
    private static final int MAX_MESSAGE_LENGTH = 51;

    String value;

    public static Message from(String value) {
        return new Message(value);
    }

    private Message(String value) {
        if (CommonValidator.isNullOrEmpty(value)) {
            throw TaskError.errMessageIsEmpty();
        }
        if (value.length() > MAX_MESSAGE_LENGTH) {
            throw TaskError.errMessageIsTooLong();
        }
        
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
