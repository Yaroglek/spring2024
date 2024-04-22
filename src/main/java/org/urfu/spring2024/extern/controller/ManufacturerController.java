package org.urfu.spring2024.extern.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urfu.spring2024.app.service.ManufacturerService;
import org.urfu.spring2024.domain.Manufacturer;
import org.urfu.spring2024.extern.assembler.ManufacturerAssembler;
import org.urfu.spring2024.extern.dto.ManufacturerDTO;

import java.util.ArrayList;

@RestController
@RequestMapping("/manufacturers")
@AllArgsConstructor
public class ManufacturerController {
    private ManufacturerService manufacturerService;
    private ManufacturerAssembler manufacturerAssembler;

    @PostMapping
    public ResponseEntity<ManufacturerDTO> createCategory(@RequestBody @Valid ManufacturerDTO manufacturerDTO) {
        Manufacturer newManufacturer = Manufacturer.builder()
                .name(manufacturerDTO.getName())
                .description(manufacturerDTO.getDescription())
                .games(new ArrayList<>())
                .build();

        manufacturerService.createManufacturer(newManufacturer);

        return new ResponseEntity<>(manufacturerAssembler.toModel(newManufacturer), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> getUserById(@PathVariable long id) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        return ResponseEntity.ok(manufacturerAssembler.toModel(manufacturer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        manufacturerService.deleteManufacturerById(id);
        return ResponseEntity.noContent().build();
    }

    //TODO
}