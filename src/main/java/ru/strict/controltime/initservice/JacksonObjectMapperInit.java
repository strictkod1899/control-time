package ru.strict.controltime.initservice;

import ru.strict.file.json.JacksonObjectMapper;
import ru.strict.ioc.annotation.Component;

public class JacksonObjectMapperInit {

    @Component
    public JacksonObjectMapper jacksonObjectMapper() {
        return new JacksonObjectMapper();
    }
}
