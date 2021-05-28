package com.berdibekov.domain.statistic;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Data
@Embeddable
public class MultipleQuestionKey implements Serializable {

    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;

    @Column(name = "option_id")
    private Long multipleOptionId;
}
