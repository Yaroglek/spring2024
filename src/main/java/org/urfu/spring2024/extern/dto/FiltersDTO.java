package org.urfu.spring2024.extern.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class FiltersDTO extends RepresentationModel<FiltersDTO> {
    @Size(max = 100)
    private String name;

    private List<Long> categoriesIds;
    
    @Min(1)
    private int amountOfPlayers;

    @Digits(integer = 3, fraction = 0)
    private int recommendedAge;

}
