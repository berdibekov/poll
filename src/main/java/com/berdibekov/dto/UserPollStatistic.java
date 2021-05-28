package com.berdibekov.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "This is User Poll Statistic")
public class UserPollStatistic {
    @ApiModelProperty
    private List<PollStatistic> pollStatistics;
}
