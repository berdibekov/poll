package com.berdibekov.repository;

import com.berdibekov.domain.statistic.MultipleQuestionsStatistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MultipleQuestionStatisticRepository extends PagingAndSortingRepository<MultipleQuestionsStatistic, Long> {
    //    @Query(value="select v.*,p.poll_id,q.question_id\n" +
//            "from options o, single_votes v,poll p,questions q\n" +
//            "where v.user_ID = ? and v.OPTION_ID = o.OPTION_ID and p.poll_id = q.poll_id ;", nativeQuery = true)
    @Query(value = "SELECT vote_id,q.question_id,poll_id,option_id,option_value FROM MULTIPLE_VOTE_OPTIONS vo\n" +
            "left join MULTIPLE_VOTE v  on MULTIPLE_VOTE_vote_id = v.vote_id\n" +
            "left join options o on o.option_id = vo.options_option_id\n" +
            "left join questions q on o.question_id = q.question_id\n" +
            "where user_id = ? ;", nativeQuery = true)
    Iterable<MultipleQuestionsStatistic> findPollsByUserId(Long userId);
}
