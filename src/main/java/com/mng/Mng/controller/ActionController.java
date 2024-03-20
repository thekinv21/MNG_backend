package com.mng.Mng.controller;

import com.mng.Mng.dto.ActionDto;
import com.mng.Mng.model.Action;
import com.mng.Mng.service.ActionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/actions")
public class ActionController {
    private final ActionService actionService;
    private final ModelMapper modelMapper;

    public ActionController(ActionService actionService, ModelMapper modelMapper) {
        this.actionService = actionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/action/{id}")
    public ResponseEntity<ActionDto> getActionById(@PathVariable String id){
        var action = actionService.getActionById(id);
        return ResponseEntity.ok(modelMapper.map(action,ActionDto.class));
    }
    @GetMapping("/action/user/{email}")
    public ResponseEntity<List<ActionDto>> getActionWithEmail(@PathVariable String email){
        var actions = actionService.getActionWithEmail(email);
        return ResponseEntity.ok(actions.stream().map(action -> modelMapper.map(action,ActionDto.class)).toList());
    }
    @GetMapping("/actions/{email}/{date}")
    public ResponseEntity<List<ActionDto>> getActionsWithDate(@PathVariable String email,@PathVariable String date){
        var actions = actionService.getActionsWithDate(email,date);
        return ResponseEntity.ok(actions.stream().map(action -> modelMapper.map(action,ActionDto.class)).toList());
    }

    @PostMapping("/create/{email}")
    public ResponseEntity<ActionDto> createAction(@PathVariable String email,@RequestBody Action action){
        var response = actionService.createAction(email,action);
        return ResponseEntity.ok(modelMapper.map(response,ActionDto.class));
    }
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ActionDto> deactivateAction(@PathVariable String id){
        var action = actionService.deactivateAction(id);
        return ResponseEntity.ok(modelMapper.map(action,ActionDto.class));
    }
    @PutMapping("/activate/{id}")
    public ResponseEntity<ActionDto> activateAction(@PathVariable String id){
        var action = actionService.activateAction(id);
        return ResponseEntity.ok(modelMapper.map(action,ActionDto.class));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAction(@PathVariable String id){
        actionService.deleteAction(id);
    }
}
