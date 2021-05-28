package com.berdibekov.domain.statistic;

import com.berdibekov.domain.Poll;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;
@Data
@Entity
public class SingleQuestionsStatistic {
    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;
    @Column(name = "poll_id")
    Long pollId;

    @Column(name = "question_id")
    Long questionId;

    @Column(name = "option_id")
    Long singleOptionId;


}
