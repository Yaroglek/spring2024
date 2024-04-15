package org.urfu.spring2024.extern.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private long id;
    private String username;
    private String email;
    private String additionalInfo;
    private List<Long> reviewsIds;
    private List<Long> trackedGamesIds;
    private List<Long> eventsAttendedIds;
}
