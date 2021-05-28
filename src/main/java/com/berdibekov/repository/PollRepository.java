package com.berdibekov.repository;


import com.berdibekov.domain.Poll;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {
    @Query(value = "select * from Poll p where p.end_date > ?", nativeQuery = true)
    Iterable<Poll> findAllActive(LocalDate date);
}
