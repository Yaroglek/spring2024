package org.urfu.spring2024.extern.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    private long id;
    private String username;
    private String email;
    private String password;
    private String additionalInfo;
    private List<Long> reviewsIDs;
    private List<Long> trackedGamesIDs;
    private List<Long> eventsAttendedIDs;
}
