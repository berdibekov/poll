package com.berdibekov.dto;

import com.berdibekov.domain.AnswerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Set;

@Data
@ApiModel(description = "This is Question Statistic")
public class QuestionStatistic {

    private long questionId;

    private AnswerType answerType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long optionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Long> optionIds;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String answer;

    @JsonIgnore
    private Long pollId;
}
