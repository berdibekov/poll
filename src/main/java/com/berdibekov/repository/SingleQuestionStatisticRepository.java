package com.berdibekov.repository;

import com.berdibekov.domain.statistic.SingleQuestionsStatistic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SingleQuestionStatisticRepository extends PagingAndSortingRepository<SingleQuestionsStatistic, Long> {
    //    @Query(value="select v.*,p.poll_id,q.question_id\n" +
//            "from options o, single_votes v,poll p,questions q\n" +
//            "where v.user_ID = ? and v.OPTION_ID = o.OPTION_ID and p.poll_id = q.poll_id ;", nativeQuery = true)
    @Query(value = "select o.*,q.poll_id from \n" +
            "(select v.vote_id,v.option_id,o.question_id,o.option_value  from (select * from single_votes where user_id = ?) v inner join options o  on v.option_id = o.option_id) o\n" +
            "left join questions q on o.question_id = q.question_id", nativeQuery = true)
    Iterable<SingleQuestionsStatistic> findPollsByUserId(Long userId);
}
