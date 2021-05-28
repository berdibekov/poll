package com.berdibekov.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "text_answers",uniqueConstraints={@UniqueConstraint(columnNames={"QUESTION_ID","USER_ID"})})
public class TextAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    @Column(name = "ANSWER_ID")
    Long id;

    @Column(name = "ANSWER_TEXT")
    String answer;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
}
