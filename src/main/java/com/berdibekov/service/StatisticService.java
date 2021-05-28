package com.berdibekov.service;

import com.berdibekov.domain.AnswerType;
import com.berdibekov.domain.statistic.MultipleQuestionsStatistic;
import com.berdibekov.domain.statistic.SingleQuestionsStatistic;
import com.berdibekov.domain.statistic.TextAnswerStatistics;
import com.berdibekov.dto.PollStatistic;
import com.berdibekov.dto.QuestionStatistic;
import com.berdibekov.repository.MultipleQuestionStatisticRepository;
import com.berdibekov.repository.SingleQuestionStatisticRepository;
import com.berdibekov.repository.TextAnswerStatisticRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {
    private Map<Long, PollStatistic> pollStatistic;

    public List<PollStatistic> getUserPollStatistics(Long userId,
                                                     SingleQuestionStatisticRepository singleQuestionStatisticRepository,
                                                     MultipleQuestionStatisticRepository multipleQuestionStatisticRepository,
                                                     TextAnswerStatisticRepository textAnswerStatisticRepository) {
        pollStatistic = new HashMap<>();
        Iterable<SingleQuestionsStatistic> singleQuestions = singleQuestionStatisticRepository.findPollsByUserId(userId);
        Iterable<MultipleQuestionsStatistic> multipleQuestions = multipleQuestionStatisticRepository.findPollsByUserId(userId);
        Iterable<TextAnswerStatistics> textAnswerQuestions = textAnswerStatisticRepository.findPollsByUserId(userId);

        addSingleQuestions(singleQuestions);
        addMultipleQuestions(multipleQuestions);
        addTextAnswers(textAnswerQuestions);
        Set<Long> keySet = pollStatistic.keySet();
        List<PollStatistic> pollStatisticList = new ArrayList<>();
        pollStatistic.keySet().forEach(pollId -> pollStatisticList.add(pollStatistic.get(pollId)));
        return pollStatisticList;
    }

    public void addSingleQuestions(Iterable<SingleQuestionsStatistic> singleQuestions) {
        for (SingleQuestionsStatistic singleQuestion : singleQuestions) {
            QuestionStatistic questionStatistic = new QuestionStatistic();
            questionStatistic.setOptionId(singleQuestion.getSingleOptionId());
            questionStatistic.setQuestionId(singleQuestion.getQuestionId());
            questionStatistic.setAnswerType(AnswerType.SINGLE);
            System.out.println(questionStatistic);
            if (pollStatistic.get(singleQuestion.getPollId()) != null) {
                pollStatistic.get(singleQuestion.getPollId()).getQuestionStatistics().add(questionStatistic);
            } else {
                PollStatistic pollStatistic = new PollStatistic(singleQuestion.getPollId());
                pollStatistic.getQuestionStatistics().add(questionStatistic);
                this.pollStatistic.put(singleQuestion.getPollId(), pollStatistic);
            }
        }
    }


    public void addMultipleQuestions(Iterable<MultipleQuestionsStatistic> multipleQuestionsStatistics) {
        HashMap<Long, Set<MultipleQuestionsStatistic>> questionStatHashMap = new HashMap<>();

        for (MultipleQuestionsStatistic multipleQuestion : multipleQuestionsStatistics) {
            Set<MultipleQuestionsStatistic> q = questionStatHashMap.get(multipleQuestion.getQuestionId());
            if (q != null) {
                q.add(multipleQuestion);
            } else {
                Set<MultipleQuestionsStatistic> newSet = new HashSet<>();
                newSet.add(multipleQuestion);
                questionStatHashMap.put(multipleQuestion.getQuestionId(), newSet);
            }
        }
        for (Long questionId : questionStatHashMap.keySet()) {
            QuestionStatistic questionStatistic = new QuestionStatistic();
            questionStatistic.setQuestionId(questionId);
            questionStatistic.setAnswerType(AnswerType.MULTIPLE);
            for (MultipleQuestionsStatistic question : questionStatHashMap.get(questionId)) {
                if (questionStatistic.getOptionIds() == null) {
                    questionStatistic.setOptionIds(new HashSet<>());
                }
                questionStatistic.getOptionIds().add(question.getMultipleQuestionKey().getMultipleOptionId());
                questionStatistic.setPollId(question.getPollId());
            }

            if (pollStatistic.get(questionStatistic.getPollId()) != null) {
                pollStatistic.get(questionStatistic.getPollId()).getQuestionStatistics().add(questionStatistic);
            } else {
                PollStatistic pollStatistic = new PollStatistic(questionStatistic.getPollId());
                pollStatistic.getQuestionStatistics().add(questionStatistic);
                this.pollStatistic.put(questionStatistic.getPollId(), pollStatistic);
            }
        }
    }

    public void addTextAnswers(Iterable<TextAnswerStatistics> textAnswerQuestions) {
        for (TextAnswerStatistics textAnswerQuestion : textAnswerQuestions) {
            QuestionStatistic questionStatistic = new QuestionStatistic();
            questionStatistic.setPollId(textAnswerQuestion.getPollId());
            questionStatistic.setAnswer(textAnswerQuestion.getAnswer());
            questionStatistic.setQuestionId(textAnswerQuestion.getQuestion().getId());
            questionStatistic.setAnswerType(AnswerType.TEXT);
            if (pollStatistic.get(textAnswerQuestion.getPollId()) != null) {
                pollStatistic.get(textAnswerQuestion.getPollId()).getQuestionStatistics().add(questionStatistic);
            } else {
                PollStatistic pollStatistic = new PollStatistic(textAnswerQuestion.getPollId());
                pollStatistic.getQuestionStatistics().add(questionStatistic);
                this.pollStatistic.put(textAnswerQuestion.getPollId(), pollStatistic);
            }
        }
    }
}
