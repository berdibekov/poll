package com.berdibekov.domain.statistic;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity

public class MultipleQuestionsStatistic {
    @EmbeddedId
    private MultipleQuestionKey multipleQuestionKey;

    @Column(name = "poll_id")
    Long pollId;

    @Column(name = "question_id")
    Long questionId;
    
//    @EmbeddedId
//    private MultipleQuestionKey multipleQuestionKey;
//
//    @GeneratedValue
//    @Column(name = "vote_id")
//    private Long id;
//    @Column(name = "poll_id")
//    Long pollId;
//
//    @Column(name = "question_id")
//    Long questionId;
//
//    @Column(name = "option_id")
//    Long multipleOptionId;
}
