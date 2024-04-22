package org.urfu.spring2024.extern.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FiltersDTO extends RepresentationModel<FiltersDTO> {
    private String name;
    private List<Long> categoriesIds;
    private int amountOfPlayers;
    private int recommendedAge;

}
