package com.berdibekov.repository;

import com.berdibekov.domain.Question;
import com.berdibekov.domain.TextAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TextAnswerRepository extends CrudRepository<TextAnswer, Long> {
    @Query(value = "SELECT t.* FROM TEXT_ANSWERS t\n" +
            "            left join questions q on t.question_id = q.question_id\n" +
            "            where user_id =? and q.question_id =? limit 1;", nativeQuery = true)
    Optional<TextAnswer> findByUserIdAndQuestionId(long userId, long questionId);
}
