package org.urfu.spring2024.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.urfu.spring2024.domain.EventStatus;
import org.urfu.spring2024.extern.dto.validation.ValueOfEnum;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventDTO extends RepresentationModel<EventDTO> {
    private long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    private LocalDateTime datetime;

    @NotBlank
    @Size(max = 200)
    private String location;

    @ValueOfEnum(enumClass = EventStatus.class, message = "Invalid Event Status")
    private String eventStatus;

    private List<Long> gamesIds;

    private List<Long> participants;
}