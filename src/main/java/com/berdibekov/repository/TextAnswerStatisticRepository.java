package com.berdibekov.repository;

import com.berdibekov.domain.statistic.TextAnswerStatistics;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TextAnswerStatisticRepository extends PagingAndSortingRepository<TextAnswerStatistics, Long> {
    //    @Query(value="select v.*,p.poll_id,q.question_id\n" +
//            "from options o, single_votes v,poll p,questions q\n" +
//            "where v.user_ID = ? and v.OPTION_ID = o.OPTION_ID and p.poll_id = q.poll_id ;", nativeQuery = true)
    @Query(value = "SELECT answer_id,user_id,q.question_id,poll_id,t.answer_text FROM TEXT_ANSWERS t\n" +
            "left join questions q on t.question_id = q.question_id\n" +
            "where user_id = ? ;", nativeQuery = true)
    Iterable<TextAnswerStatistics> findPollsByUserId(Long userId);
}
