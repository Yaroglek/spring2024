package org.urfu.spring2024.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionDTO extends RepresentationModel<DiscussionDTO> {
    private long id;

    @NotNull
    private long topicStarterId;

    @NotNull
    private long boardGameId;

    @NotNull
    private LocalDateTime createdDttm;

    @NotBlank
    @Size(max = 100)
    private String topic;

    @NotNull
    private boolean isOpen;

    private List<Long> messagesIds;
}
