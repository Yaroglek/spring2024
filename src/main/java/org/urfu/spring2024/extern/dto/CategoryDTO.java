package org.urfu.spring2024.extern.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDTO extends RepresentationModel<CategoryDTO> {
    private long id;
    private String name;
    private String description;
    private List<Long> gamesIds;
}