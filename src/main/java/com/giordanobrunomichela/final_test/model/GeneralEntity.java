package com.giordanobrunomichela.final_test.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class GeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "general_entity_entity_type",
        joinColumns = @JoinColumn(name = "general_entity_id"), 
        inverseJoinColumns = @JoinColumn(name = "general_entity_type_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"general_entity_id", "general_entity_type_id"})
    )
    private List<GeneralEntityType> generalEntityType;
}
