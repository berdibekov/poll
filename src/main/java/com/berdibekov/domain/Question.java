package com.berdibekov.domain;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Entity
@Table(name = "QUESTIONS")
@ApiModel(description = "This is Question Entity options must be > 1 , but < 6  ")
public class Question {
    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long id;

    @ApiModelProperty(example = "this is example")
    @Column(name = "QUESTION")
    @NotNull
    private String question;

    @NotNull
    @Column(name = "ANSWER_TYPE")
    private AnswerType answerType;


    //    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "QUESTION_ID")
    @OrderBy
//    @Size(min = 2, max = 6)
    private Set<Option> options;

}
