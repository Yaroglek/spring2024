package org.urfu.spring2024.extern.assembler;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import org.urfu.spring2024.domain.BoardGame;
import org.urfu.spring2024.domain.Manufacturer;
import org.urfu.spring2024.extern.controller.ManufacturerController;
import org.urfu.spring2024.extern.dto.ManufacturerDTO;

import java.util.stream.Collectors;

@Component
public class ManufacturerAssembler extends RepresentationModelAssemblerSupport<Manufacturer, ManufacturerDTO> {
    public ManufacturerAssembler() {
        super(ManufacturerController.class, ManufacturerDTO.class);
    }

    @Override
    public ManufacturerDTO toModel(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = instantiateModel(manufacturer);

        manufacturerDTO.setId(manufacturer.getId());
        manufacturerDTO.setName(manufacturer.getName());
        manufacturerDTO.setDescription(manufacturer.getDescription());
        manufacturerDTO.setGamesIDs(manufacturer.getGames().stream()
                .map(BoardGame::getId)
                .collect(Collectors.toList()));

        return manufacturerDTO;
    }
}
