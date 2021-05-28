package com.berdibekov.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Data
@ApiModel(description = "This is Poll Statistic")
public class PollStatistic {
    @Id
    @ApiModelProperty(example = "this is example")
    private long pollId;

    @ApiModelProperty(example = "this is example")
    private Set<QuestionStatistic> questionStatistics = new HashSet<>();

    public PollStatistic(Long pollId) {
        this.pollId = pollId;
    }
}
