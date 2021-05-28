package com.berdibekov.api.controller.auth;

import com.berdibekov.domain.Question;
import com.berdibekov.domain.User;
import com.berdibekov.dto.PollStatistic;
import com.berdibekov.dto.UserPollStatistic;
import com.berdibekov.dto.error.ErrorDetail;
import com.berdibekov.repository.MultipleQuestionStatisticRepository;
import com.berdibekov.repository.SingleQuestionStatisticRepository;
import com.berdibekov.repository.TextAnswerStatisticRepository;
import com.berdibekov.repository.UserRepository;
import com.berdibekov.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("computeResultControllerV3")
@RequestMapping({"/auth/api/"})
@Api(value = "computeresult")
public class ComputeResultController {

    private SingleQuestionStatisticRepository singleQuestionStatisticRepository;
    private MultipleQuestionStatisticRepository multipleQuestionStatisticRepository;
    private TextAnswerStatisticRepository textAnswerStatisticRepository;
    private UserRepository userRepository;
    private StatisticService statisticService;
    private UserDetails userDetails;
    public ComputeResultController(SingleQuestionStatisticRepository singleQuestionStatisticRepository,
                                   MultipleQuestionStatisticRepository multipleQuestionStatisticRepository,
                                   TextAnswerStatisticRepository textAnswerStatisticRepository,
                                   UserRepository userRepository,
                                   StatisticService statisticService ) {
        this.singleQuestionStatisticRepository = singleQuestionStatisticRepository;
        this.multipleQuestionStatisticRepository = multipleQuestionStatisticRepository;
        this.textAnswerStatisticRepository = textAnswerStatisticRepository;
        this.statisticService = statisticService;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves poll statistic for authenticated user", response = UserPollStatistic.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok", response = UserPollStatistic.class),
            @ApiResponse(code = 404, message = "", response = ErrorDetail.class)})
    public ResponseEntity<?> getUserStatistic() {
        userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<PollStatistic> statistic = statisticService.getUserPollStatistics(user.getId(),
                                                                              singleQuestionStatisticRepository,
                                                                              multipleQuestionStatisticRepository,
                                                                              textAnswerStatisticRepository);
        UserPollStatistic userPollStatistic = new UserPollStatistic();
        userPollStatistic.setPollStatistics(statistic);
        return new ResponseEntity<>(userPollStatistic, HttpStatus.OK);
    }
}