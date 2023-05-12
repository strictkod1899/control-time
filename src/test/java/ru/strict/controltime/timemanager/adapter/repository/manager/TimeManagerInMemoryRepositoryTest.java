package ru.strict.controltime.timemanager.adapter.repository.manager;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.strict.controltime.timemanager.testdouble.stub.entity.TimeManagerStub;

import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class TimeManagerInMemoryRepositoryTest {

    TimeManagerInMemoryRepository timeManagerRepository;

    @BeforeEach
    void setupTest() {
        timeManagerRepository = new TimeManagerInMemoryRepository();
    }

    @Test
    void testGetActiveManager_ManagerNotExists_ReturnEmptyOptional() {
        var expectedTimeManager = TimeManagerStub.getFullTimeManager();

        var activeManagerOptional = timeManagerRepository.getActiveManager();
        assertTrue(activeManagerOptional.isEmpty());

        timeManagerRepository.setActiveManager(expectedTimeManager);

        activeManagerOptional = timeManagerRepository.getActiveManager();
        assertTrue(activeManagerOptional.isPresent());
        assertEquals(expectedTimeManager, activeManagerOptional.get());
    }

    @Test
    void testGetActiveManager_ManagerExists_ReturnTimeManager() {
        var givenTimeManager1 = TimeManagerStub.getEmptyTimeManager();
        var expectedTimeManager = TimeManagerStub.getFullTimeManager();

        timeManagerRepository.setActiveManager(givenTimeManager1);

        var activeManagerOptional = timeManagerRepository.getActiveManager();
        assertTrue(activeManagerOptional.isPresent());
        assertEquals(givenTimeManager1, activeManagerOptional.get());

        timeManagerRepository.setActiveManager(expectedTimeManager);

        activeManagerOptional = timeManagerRepository.getActiveManager();
        assertTrue(activeManagerOptional.isPresent());
        assertEquals(expectedTimeManager, activeManagerOptional.get());
    }
}
