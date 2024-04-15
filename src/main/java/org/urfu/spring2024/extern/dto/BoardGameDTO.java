package org.urfu.spring2024.extern.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.List;

@Data
public class BoardGameDTO extends RepresentationModel<BoardGameDTO> {
    private long id;
    private String name;
    private LocalDate releaseDate;
    private long manufacturerId;
    private String description;
    private int recommendedAge;
    private int amountOfPlayers;
    private List<Long> categoriesIds;
    private List<Long> reviewsIds;
    private List<Long> eventsIds;
    private List<Long> discussionsIds;
}
