package com.berdibekov.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "OPTIONS")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    @Column(name = "OPTION_ID")
    private Long id;

    @Column(name = "OPTION_VALUE")
    private String value;

//    @JoinTable(name = "QESTIONS",joinColumns = {@JoinColumn(name = "QUESTION_ID")})
//    Long question;
//    @JoinColumn(name = "OPTION_ID")
//    @OneToMany
//    private Set<SingleVote> singleVote;
}
