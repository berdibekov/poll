package com.berdibekov.api.controller.auth;

import com.berdibekov.domain.Poll;
import com.berdibekov.domain.Question;
import com.berdibekov.dto.error.ErrorDetail;
import com.berdibekov.exception.ResourceNotFoundException;
import com.berdibekov.exception.ValidationException;
import com.berdibekov.repository.PollRepository;
import com.berdibekov.repository.QuestionRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping({"/auth/api/"})
@RestController("PollControllerV3")
public class PollController {

    private PollRepository pollRepository;
    private QuestionRepository questionRepository;

    public PollController(PollRepository pollRepository, QuestionRepository questionRepository) {
        this.pollRepository = pollRepository;
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/active.polls", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all active polls for current date", response = Poll.class)
    public ResponseEntity<?> getAllActivePolls() {
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        Iterable<Poll> p = pollRepository.findAllActive(currentDate);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all polls for authenticated user", response = Poll.class)

    public ResponseEntity<?> getAllPolls() {
        Iterable<Poll> p = pollRepository.findAll();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority({'ROLE_ADMIN'})")
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves given Poll by id for authenticated", response = Poll.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "", response = Poll.class),
            @ApiResponse(code = 404, message = "Unable to find Poll", response = ErrorDetail.class)})

    public ResponseEntity<?> getPollById(@PathVariable Long pollId) {
        Optional<Poll> p = pollRepository.findById(pollId);
        if (p.isPresent()) {
            return new ResponseEntity<>(p.get(), HttpStatus.OK);
        }
        throw new ResourceNotFoundException("poll with id : " + pollId + " not found");
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    @ApiOperation(value = "Creates a new Poll with questions")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Poll Created Successfully"),
            @ApiResponse(code = 500, message = "Error creating Poll", response = ErrorDetail.class)})

    public ResponseEntity<Void> createPoll(@Valid @RequestBody Poll poll) {
        poll = pollRepository.save(poll);
        HttpHeaders responseHeaders = getHttpHeadersForNewResours(poll.getId());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    private HttpHeaders getHttpHeadersForNewResours(Long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        responseHeaders.setLocation(newPollUri);
        return responseHeaders;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls", method = RequestMethod.PUT)
    @ApiOperation(value = "Updates given Poll")
    @ApiResponses(value = {@ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Unable to find Question", response = ErrorDetail.class)})

    public ResponseEntity<Void> updatePoll(@RequestBody Poll poll) {
        Optional<Poll> p = pollRepository.findById(poll.getId());

        if (p.isEmpty()) {
            throw new ResourceNotFoundException("poll with id : " + poll.getId() + " not found");
        }
        Poll pollInDatabase = p.get();
        if (!pollInDatabase.getStartDate().equals(poll.getStartDate())) {
            throw new ValidationException("It is forbidden to change start day after poll creation");
        }
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Deletes given Poll")
    @ApiResponses(value = {@ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "Unable to find Poll", response = ErrorDetail.class)})

    public ResponseEntity<Void> deleteQuestion(@PathVariable Long pollId) {
        if (!pollRepository.existsById(pollId)) {
            throw new ResourceNotFoundException("poll with id : " + pollId + " not found");
        }
        Optional<Poll> p = pollRepository.findById(pollId);
        Poll poll = p.get();
        questionRepository.deleteAll(poll.getQuestions());
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
