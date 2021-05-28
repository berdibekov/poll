package com.berdibekov.repository;

import com.berdibekov.domain.Option;
import com.berdibekov.domain.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    @Query(value = "select q.* from questions q\n" +
            " join options o ON o.question_id = q.question_id\n" +
            "where o.option_id in :options", nativeQuery = true)
    List<Question> findAllByOptions(@Param("options") List<Option> options);

    @Query(value = "select q.* \n" +
            "from \n" +
            "SINGLE_VOTES v \n" +
            "left join \n" +
            "options o on v.option_id = o.option_id\n" +
            "left join\n" +
            "questions q on q.question_id = o.question_id\n" +
            "where v.user_id = ?2 and o.option_id= ?1 limit 1", nativeQuery = true)
    Optional<Question> findByOptionIdAndUserId(long optionId, long userId);

    @Query(value = "select q.*\n" +
            "from \n" +
            "options o \n" +
            "left join\n" +
            "QUESTIONS  q on q.question_id = o.question_id\n" +
            "where o.option_id= ? limit 1 ", nativeQuery = true)
    Optional<Question> findByOptionId(long optionId);

//    @Query(value = "select q from options o \n" +
//            "left join questions q\n" +
//            " on q.question_id = o.option_id\n" +
//            "where option_id in :options ")
//    Iterable<Question> findByOptions(@Param("options") Collection<Option> values);
}
