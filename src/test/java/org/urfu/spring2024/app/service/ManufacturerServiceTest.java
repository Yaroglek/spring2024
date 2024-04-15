package org.urfu.spring2024.app.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.urfu.spring2024.app.repository.ManufacturerRepository;
import org.urfu.spring2024.domain.Manufacturer;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
class ManufacturerServiceTest {
    @Mock
    private ManufacturerRepository manufacturerRepository;

    @InjectMocks
    private ManufacturerService manufacturerService;

    @Test
    public void testCreateManufacturer() {
        Manufacturer manufacturer = Manufacturer.builder().id(1L).name("manufacturer1").build();

        when(manufacturerRepository.save(manufacturer)).thenReturn(manufacturer);
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);

        assertNotNull(createdManufacturer.getId());
    }

    @Test
    public void testManufacturerNotCreated() {
        assertThrows(IllegalArgumentException.class, () -> manufacturerService.createManufacturer(null));
    }

    @Test
    public void testGetManufacturerByID() {
        Manufacturer manufacturer = Manufacturer.builder().id(1L).name("manufacturer1").build();

        when(manufacturerRepository.findById(1L)).thenReturn(Optional.of(manufacturer));
        Manufacturer searchedManufacturer = manufacturerService.getManufacturerById(1L);

        assertNotNull(searchedManufacturer);
        assertEquals(1L, searchedManufacturer.getId());
    }

    @Test
    public void testManufacturerNotFoundByID() {
        when(manufacturerRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> manufacturerService.getManufacturerById(5L));
    }

    @Test
    public void testDeleteManufacturerByID() {
        Manufacturer manufacturer = Manufacturer.builder().id(2L).name("manufacturer2").build();

        when(manufacturerRepository.findById(2L)).thenReturn(Optional.of(manufacturer));
        manufacturerService.deleteManufacturerById(2L);

        verify(manufacturerRepository, times(1)).deleteById(2L);
    }
}
