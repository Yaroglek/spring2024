package org.urfu.spring2024.extern.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ManufacturerDTO extends RepresentationModel<ManufacturerDTO> {
    private long id;
    private String name;
    private String description;
    private List<Long> gamesIDs;
}
