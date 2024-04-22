package org.urfu.spring2024.extern.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoardGameDTO extends RepresentationModel<BoardGameDTO> {
    private long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private LocalDate releaseDate;

    private long manufacturerId;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @Digits(integer = 3, fraction = 0)
    private int recommendedAge;

    @Min(1)
    private int amountOfPlayers;

    private List<Long> categoriesIds;

    private List<Long> reviewsIds;

    private List<Long> eventsIds;

    private List<Long> discussionsIds;
}
