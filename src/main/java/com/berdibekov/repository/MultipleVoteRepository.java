package com.berdibekov.repository;

import com.berdibekov.domain.MultipleVote;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MultipleVoteRepository extends PagingAndSortingRepository<MultipleVote, Long> {
}
