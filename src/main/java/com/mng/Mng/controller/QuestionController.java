package com.mng.Mng.controller;

import com.mng.Mng.dto.QuestionDto;
import com.mng.Mng.dto.request.CreateQuestionRequest;
import com.mng.Mng.service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/api/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    public QuestionController(QuestionService questionService, ModelMapper modelMapper) {
        this.questionService = questionService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/question/add/{actionId}")
    public ResponseEntity<List<QuestionDto>> addQuestionsToAction(@PathVariable String actionId,
                                                                  @RequestBody List<CreateQuestionRequest> request) {
        var savedQuestions = questionService.addQuestionsToAction(actionId, request);
        List<CreateQuestionRequest> requests = request.stream()
                .map(dto -> modelMapper.map(dto, CreateQuestionRequest.class))
                .toList();
        List<QuestionDto> questionDTOs = savedQuestions.stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(questionDTOs);

    }
}
