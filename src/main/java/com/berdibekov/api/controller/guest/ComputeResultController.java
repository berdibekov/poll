package com.berdibekov.api.controller.guest;

import com.berdibekov.domain.Option;
import com.berdibekov.domain.SingleVote;
import com.berdibekov.domain.User;
import com.berdibekov.dto.PollStatistic;
import com.berdibekov.exception.IncorrectActionException;
import com.berdibekov.repository.*;
import com.berdibekov.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("computeResultControllerGuest")
@RequestMapping({"/guest/api/"})
@Api(value = "computeresult")
public class ComputeResultController {

    private SingleQuestionStatisticRepository singleQuestionStatisticRepository;
    private MultipleQuestionStatisticRepository multipleQuestionStatisticRepository;
    private TextAnswerStatisticRepository textAnswerStatisticRepository;
    private SingleVoteRepository singleVoteRepository;
    private UserRepository userRepository;
    private StatisticService statisticService;

    public ComputeResultController(SingleQuestionStatisticRepository singleQuestionStatisticRepository,
                                   MultipleQuestionStatisticRepository multipleQuestionStatisticRepository,
                                   TextAnswerStatisticRepository textAnswerStatisticRepository,
                                   SingleVoteRepository singleVoteRepository,
                                   UserRepository userRepository,
                                   StatisticService statisticService) {
        this.singleQuestionStatisticRepository = singleQuestionStatisticRepository;
        this.multipleQuestionStatisticRepository = multipleQuestionStatisticRepository;
        this.textAnswerStatisticRepository = textAnswerStatisticRepository;
        this.singleVoteRepository = singleVoteRepository;
        this.userRepository = userRepository;
        this.statisticService = statisticService;
    }

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    @ApiOperation(value = "Computes the results of a given User", response = Object.class)
    public ResponseEntity<?> computeResult(@RequestParam Long userId) {
        User anonymousUser = getAnonymousUserById(userId);

        HashMap<SingleVote, Option> singleVoteOptionHashMap = new HashMap<>();
        Iterable<SingleVote> s = singleVoteRepository.findAllByUserId(anonymousUser.getId());
        for (SingleVote singleVote : s) {
            singleVoteOptionHashMap.put(singleVote, singleVote.getOption());
        }

        List<PollStatistic> userStatistic = statisticService.getUserPollStatistics(anonymousUser.getId(),
                                                                                   singleQuestionStatisticRepository,
                                                                                   multipleQuestionStatisticRepository,
                                                                                   textAnswerStatisticRepository);
        return new ResponseEntity<>(userStatistic, HttpStatus.OK);
    }

    private User getAnonymousUserById(@RequestParam Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User anonymousUser;
        if (user.isEmpty()) {
            anonymousUser = new User();
            anonymousUser.setAnonymous(true);
            anonymousUser.setId(userId);
            anonymousUser.setFirstName("");
            anonymousUser.setLastName("");
            anonymousUser.setPassword("");
            anonymousUser.setUsername(Long.toString(userId));
            anonymousUser.setRoles(new HashSet<>());
            anonymousUser = userRepository.save(anonymousUser);
        } else {
            if (!user.get().isAnonymous()) {
                throw new IncorrectActionException("Trying to cast vote for registered user,please please log in");
            }
            anonymousUser = user.get();
        }
        return anonymousUser;
    }
}