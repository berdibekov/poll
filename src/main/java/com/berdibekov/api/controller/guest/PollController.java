package com.berdibekov.api.controller.guest;

import com.berdibekov.domain.Poll;
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

@RequestMapping({"/guest/api/"})
@RestController("PollControllerGuest")
public class PollController {

    private PollRepository pollRepository;
    private QuestionRepository questionRepository;

    public PollController(PollRepository pollRepository, QuestionRepository questionRepository) {
        this.pollRepository = pollRepository;
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/active.polls", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieves all active polls", response = Poll.class)
    public ResponseEntity<?> getAllActivePolls() {
        LocalDate currentDate = LocalDateTime.now().toLocalDate();
        Iterable<Poll> p = pollRepository.findAllActive(currentDate);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
}
