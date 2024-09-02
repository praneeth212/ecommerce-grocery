package com.grocery.servicetests;

import com.grocery.model.Units;
import com.grocery.repository.UnitsRepository;
import com.grocery.service.UnitsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitsServiceTests {

    @Mock
    private UnitsRepository unitsRepository;

    @InjectMocks
    private UnitsService unitsService;

    private Units unit;

    @BeforeEach
    void setUp() {
        unit = new Units();
        unit.setUnitName("Test Unit");
    }

    @Test
    void testFindByUnitName() {
        when(unitsRepository.findByUnitName("Test Unit")).thenReturn(Optional.of(unit));

        Optional<Units> foundUnit = unitsService.findByUnitName("Test Unit");

        assertTrue(foundUnit.isPresent());
        assertEquals("Test Unit", foundUnit.get().getUnitName());
        verify(unitsRepository, times(1)).findByUnitName("Test Unit");
    }

    @Test
    void testSaveUnit() {
        when(unitsRepository.save(any(Units.class))).thenReturn(unit);

        Units savedUnit = unitsService.save(unit);

        assertEquals("Test Unit", savedUnit.getUnitName());
        verify(unitsRepository, times(1)).save(unit);
    }
}
