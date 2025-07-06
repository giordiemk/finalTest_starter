package com.giordanobrunomichela.final_test.dto;

import lombok.Data;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class GeneralEntityDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(example = "Entity Name", description = "Name of the general entity")
    private String name;
    @Schema(example = "This is a general entity", description = "Description of the general entity")
    private String description;
    @Schema(example = "[1]", description = "List of IDs of the general entity types associated with this entity")
    private List<Long> generalEntityTypeIds;
}
