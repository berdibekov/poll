package com.berdibekov.domain.statistic;

import com.berdibekov.domain.Question;
import com.berdibekov.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TextAnswerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    Long id;

    @Column(name = "POLL_ID")
    private Long pollId;

    @Column(name = "ANSWER_TEXT")
    String answer;

    @OneToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;
}
