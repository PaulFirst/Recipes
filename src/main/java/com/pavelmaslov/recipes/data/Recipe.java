package com.pavelmaslov.recipes.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name="recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(nullable = false)
    int id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String category;

    @Column
    private LocalDateTime date;

    @JsonIgnore
    @Column
    private String author;

    @NotEmpty
    @ElementCollection
    @CollectionTable
    @Column(nullable = false)
    private List<String> ingredients = new ArrayList<>();

    @NotEmpty
    @ElementCollection
    @CollectionTable
    @Column(nullable = false)
    private List<String> directions = new ArrayList<>();
}

