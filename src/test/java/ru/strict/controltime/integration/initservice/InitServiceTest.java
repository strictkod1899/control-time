package ru.strict.controltime.integration.initservice;

import org.junit.jupiter.api.Test;
import ru.strict.controltime.initservice.IoC;

public class InitServiceTest {

    @Test
    void testInit_ValidParams_NoError() {
        IoC.instance();
    }
}
