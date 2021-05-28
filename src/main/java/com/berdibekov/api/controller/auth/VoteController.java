package com.berdibekov.api.controller.auth;

import com.berdibekov.domain.*;
import com.berdibekov.exception.IncorrectActionException;
import com.berdibekov.exception.ResourceNotFoundException;
import com.berdibekov.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequestMapping({"/auth/api/"})
@RestController("voteControllerV3")
@Api(value = "votes")
public class VoteController {

    private SingleVoteRepository singleVoteRepository;


    private UserRepository userRepository;
    private OptionRepository optionRepository;
    private MultipleVoteRepository multipleVoteRepository;
    private QuestionRepository questionRepository;
    private TextAnswerRepository textAnswerRepository;

    public VoteController(SingleVoteRepository singleVoteRepository, OptionRepository optionRepository,
                          MultipleVoteRepository multipleVoteRepository,
                          QuestionRepository questionRepository,
                          TextAnswerRepository textAnswerRepository,
                          @Qualifier("PollUserDetailsService") UserDetailsService userDetailsService,
                          UserRepository userRepository) {
        this.singleVoteRepository = singleVoteRepository;
        this.userRepository = userRepository;
        this.optionRepository = optionRepository;
        this.multipleVoteRepository = multipleVoteRepository;
        this.questionRepository = questionRepository;
        this.textAnswerRepository = textAnswerRepository;
    }

    @RequestMapping(value = "/polls/questions/single/votes", method = RequestMethod.POST)
    @ApiOperation(value = "Casts a new single option vote for a given option")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Vote Created Successfully")})

    public ResponseEntity<Void> createSingleVote(@RequestParam Long optionId) {
        User user = getCurrentAuthonicatedUser();
        Optional<Option> o = optionRepository.findById(optionId);
        ValidateSingleVoteRequest(optionId, user, o);
        Option option = o.get();
        SingleVote singleVote = new SingleVote();
        singleVote.setUser(user);
        singleVote.setOption(option);
        singleVote = singleVoteRepository.save(singleVote);
        HttpHeaders responseHeaders = getHttpHeadersForCreatedResourse(singleVote.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    private User getCurrentAuthonicatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    private void ValidateSingleVoteRequest(@RequestParam Long optionId, User user, Optional<Option> o) {
        if (o.isEmpty()) {
            throw new ResourceNotFoundException("option with id : " + optionId + " not found");
        }
        Optional<Question> q = questionRepository.findByOptionId(optionId);
        if (q.isEmpty()) {
            throw new ResourceNotFoundException("Еhe question that the option with id : " +
                                                        optionId + " refers to doesn`t existd");
        }
        if (!q.get().getAnswerType().equals(AnswerType.SINGLE)) {
            throw new IncorrectActionException("This request is suitable only for single option question only" + q.get().getId());
        }
        Set<Option> options = q.get().getOptions();
        for (Option option : options) {
            q = questionRepository.findByOptionIdAndUserId(option.getId(), user.getId());
            if (q.isPresent()) {
                throw new IncorrectActionException("user already gave his answer for question with id : " + q.get().getId());
            }
        }
    }

    @RequestMapping(value = "/polls/questions/multiple/votes", method = RequestMethod.POST)
    @ApiOperation(value = "Casts a new multiple option votes for a given options ids")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Multiple vote Created Successfully")})
    public ResponseEntity<Void> createMultipleVote(@RequestBody Set<Long> optionIds) {
        User user = getCurrentAuthonicatedUser();
        Set<Option> options = getOptionsFromRepository(optionIds);
        validateMultipleVoteRequest(optionIds, options);
        MultipleVote multipleVote = new MultipleVote();
        multipleVote.setUser(user);
        multipleVote.setOptions(options);
        multipleVote = multipleVoteRepository.save(multipleVote);
        HttpHeaders responseHeaders = getHttpHeadersForCreatedResourse(multipleVote.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    private Set<Option> getOptionsFromRepository(@RequestBody Set<Long> optionIds) {
        Iterable<Option> o = optionRepository.findAllById(optionIds);
        Set<Option> options = new HashSet<>();
        o.forEach(option -> options.add(option));
        return options;
    }

    private void validateMultipleVoteRequest(@RequestBody Set<Long> optionIds, Set<Option> options) {
        if (options.size() != optionIds.size()) {
            throw new ResourceNotFoundException("some options not found");
        }
        if (isOptionsFromDifferentQuestions(options)) {
            throw new IncorrectActionException("Options must belong to one question");
        }
        if (!questionRepository.findByOptionId(options.iterator().next().getId()).get().getAnswerType().equals(AnswerType.MULTIPLE)) {
            throw new IncorrectActionException("This question is not for multiple vote");
        }
    }

    private boolean isOptionsFromDifferentQuestions(Set<Option> options) {
        Set<Long> questionIds = new HashSet<>();
        for (Option option : options) {
            Optional<Question> q = questionRepository.findByOptionId(option.getId());
            q.ifPresent(question -> questionIds.add(question.getId()));
        }
        return (questionIds.size() > 1);
    }

    @RequestMapping(value = "polls/questions/{questionId}/answer", method = RequestMethod.POST)
    @ApiOperation(value = "Casts a new text answer for a given question")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Vote Created Successfully")})

    public ResponseEntity<Void> createTextAnswer(@PathVariable Long questionId, @RequestBody String answer) {
        TextAnswer textAnswer = new TextAnswer();
        User user = getCurrentAuthonicatedUser();
        Optional<Question> q = questionRepository.findById(questionId);
        validateTextAnswerRequest(questionId, user, q);
        Question question = q.get();
        textAnswer.setUser(user);
        textAnswer.setQuestion(question);
        textAnswer.setAnswer(answer);
        textAnswer = textAnswerRepository.save(textAnswer);
        HttpHeaders responseHeaders = getHttpHeadersForCreatedResourse(textAnswer.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    private void validateTextAnswerRequest(@PathVariable Long questionId, User user, Optional<Question> q) {
        if (q.isEmpty()) {
            throw new ResourceNotFoundException("Question with id = " + questionId + " not found");
        }
        if (!q.get().getAnswerType().equals(AnswerType.TEXT)) {
            throw new IncorrectActionException("This request is suitable only for text option question only" + q.get().getId());
        }
        Optional<TextAnswer> t = textAnswerRepository.findByUserIdAndQuestionId(user.getId(), questionId);
        if (t.isPresent()) {
            throw new IncorrectActionException("answer to question with id : " + questionId +
                                                       " was already added by user with id : " + user.getId());
        }
    }

    private HttpHeaders getHttpHeadersForCreatedResourse(Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                            .buildAndExpand(id).toUri());
        return responseHeaders;
    }
}
