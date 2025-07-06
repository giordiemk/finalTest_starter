package com.giordanobrunomichela.final_test.dto;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @Schema(example = "John Doe", description = "Name of the user")
    private String name;
    @Schema(example = "johndoe", description = "Username of the user")
    private String username;
    @Schema(example = "email@email.com", description = "Email of the user")
    private String email;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean deleted;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<String> roles;
}