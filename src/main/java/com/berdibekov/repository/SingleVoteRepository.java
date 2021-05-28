package com.berdibekov.repository;


import org.springframework.data.jpa.repository.Query;

import com.berdibekov.domain.SingleVote;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SingleVoteRepository extends PagingAndSortingRepository<SingleVote, Long> {

	@Query(value="select v.* from Option o, Vote v where o.POLL_ID = ?1 and v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
	Iterable<SingleVote> findByPoll(Long pollId);

	@Query(value="select v.* from Options o, Single_votes v where v.USER_ID = ? and v.OPTION_ID = o.OPTION_ID", nativeQuery = true)
	Iterable<SingleVote> findAllByUserId(Long userId);
	
}
