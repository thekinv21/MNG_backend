package com.mng.Mng.service;

import com.mng.Mng.dto.request.CreateQuestionRequest;
import com.mng.Mng.exception.ActionNotFoundException;
import com.mng.Mng.model.Action;
import com.mng.Mng.model.Question;
import com.mng.Mng.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ActionService actionService;

    public QuestionService(QuestionRepository questionRepository, ActionService actionService) {
        this.questionRepository = questionRepository;
        this.actionService = actionService;
    }



    public List<Question> addQuestionsToAction(String actionId, List<CreateQuestionRequest> requests) {
        // Question ve Action null kontrolü yapılmalı
        Optional<Action> actionOptional = Optional.ofNullable(actionService.getActionById(actionId));
        if (actionOptional.isEmpty()) throw new ActionNotFoundException("Action not found");

        var action = actionOptional.get();

        List<Question> questions = requests.stream()
                .map(request -> {
                    var question = new Question(request.getQuestion(), request.getAnswer());
                    question.setAction(action);
                    action.addQuestion(question);
                    return question;
                })
                .collect(Collectors.toList());
        return questionRepository.saveAll(questions);
    }

}


