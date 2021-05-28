package com.berdibekov.api.controller.auth;

import com.berdibekov.domain.Poll;
import com.berdibekov.domain.Question;
import com.berdibekov.dto.error.ErrorDetail;
import com.berdibekov.exception.ResourceNotFoundException;
import com.berdibekov.repository.PollRepository;
import com.berdibekov.repository.QuestionRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RequestMapping({"/auth/api/"})
@RestController("questionControllerV3")
@Api(value = "Question controller")
public class QuestionController {

    private QuestionRepository questionRepository;
    private PollRepository pollRepository;

    public QuestionController(QuestionRepository questionRepository, PollRepository pollRepository) {
        this.questionRepository = questionRepository;
        this.pollRepository = pollRepository;
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
    @RequestMapping(value = "polls/{pollId}/questions", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Question")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll Created Successfully"),
            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class)})

    public ResponseEntity<Void> createQuestion(@PathVariable(value = "pollId") Long pollId,
                                               @Valid @RequestBody Question question) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll.isEmpty()) {
            throw new ResourceNotFoundException("Poll with id = " + pollId + " not Found");
        }
        question = questionRepository.save(question);
        poll.get().getQuestions().add(question);
        pollRepository.save(poll.get());
        HttpHeaders responseHeaders = getHttpHeadersForNewlyCreatedResource(question.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    private HttpHeaders getHttpHeadersForNewlyCreatedResource(Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        responseHeaders.setLocation(newPollUri);
        return responseHeaders;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/questions/{questionId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves given question", response = Question.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = Question.class),
            @ApiResponse(code = 404, message = "Unable to find Question", response = ErrorDetail.class)})

    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) {
        verifyQuestion(questionId);
        Optional<Question> p = questionRepository.findById(questionId);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all the questions", response = Question.class, responseContainer = "List")

    public ResponseEntity<Page<Question>> getAllQuestions(Pageable pageable) {
        Page<Question> allPolls = questionRepository.findAll(pageable);
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/questions/{questionsId}", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates given Question")
    @ApiResponses(value = {@ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Unable to find Question", response = ErrorDetail.class)})

    public ResponseEntity<Void> updateQuestion(@RequestBody @Valid Question question,
                                               @PathVariable Long questionsId) {
        verifyQuestion(questionsId);
        questionRepository.save(question);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/questions/{questionsId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes given Question")
    @ApiResponses(value = {@ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Unable to find Question", response = ErrorDetail.class)})

    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionsId) {
        verifyQuestion(questionsId);
        questionRepository.deleteById(questionsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected void verifyQuestion(Long questionsId) throws ResourceNotFoundException {
        Optional<Question> question = questionRepository.findById(questionsId);
        if (question == null) {
            throw new ResourceNotFoundException("Question with id " + questionsId + " not found");
        }
    }
}
