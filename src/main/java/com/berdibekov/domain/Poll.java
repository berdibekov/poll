package com.berdibekov.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@ApiModel(description = "This is Poll Entity")
public class Poll {
    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POLL_ID")
    private Long id;

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @OrderBy
    @Valid
    @JoinColumn(name = "POLL_ID")
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Question> questions;
}
