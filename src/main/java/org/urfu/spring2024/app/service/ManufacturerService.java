package org.urfu.spring2024.app.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.urfu.spring2024.app.repository.ManufacturerRepository;
import org.urfu.spring2024.domain.Manufacturer;

@Slf4j
@Service
@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    /**
     * Сохранение в БД нового производителя.
     *
     * @param manufacturer - объект производителя для сохранения в БД.
     * @return - сохраненный производитель.
     */
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Производитель не может быть null");
        }
        try {
            manufacturerRepository.save(manufacturer);
            log.info("Создан производитель с ID {}", manufacturer.getId());
            return manufacturer;
        } catch (Exception e) {
            throw new RuntimeException("Произошла ошибка при сохранении нового производителя", e);
        }
    }

    /**
     * Поиск производителя в БД по его id.
     *
     * @param manufacturerID - уникальный идентификатор для поиска производителя.
     * @return - производитель с указанным id.
     */
    public Manufacturer getManufacturerById(long manufacturerID) {
        var searchedManufacturer = manufacturerRepository.findById(manufacturerID)
                .orElseThrow(() -> new IllegalArgumentException("Производитель с ID " + manufacturerID + " не найден"));
        log.debug("Производитель с ID {} найден", manufacturerID);
        return searchedManufacturer;
    }

    /**
     * Удаление производителя из БД по её id.
     *
     * @param manufacturerID - уникальный идентификатор для поиска производителя.
     */
    public void deleteManufacturerById(long manufacturerID) {
        manufacturerRepository.deleteById(manufacturerID);
        log.info("Производитель с ID {} удален", manufacturerID);
    }
}
