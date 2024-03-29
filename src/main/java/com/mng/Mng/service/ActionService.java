package com.mng.Mng.service;

import com.mng.Mng.model.Action;
import com.mng.Mng.repository.ActionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ActionService {
    private final ActionRepository actionRepository;
    private final UserService userService;

    public ActionService(ActionRepository actionRepository, UserService userService) {
        this.actionRepository = actionRepository;
        this.userService = userService;
    }
    public Action getActionById(String id){
        return actionRepository.findById(id).orElse(null);
    }

    public List<Action> getActionWithEmail(String email){
        var currentDate = LocalDateTime.now();
        var user = userService.getUserByEmail(email);
        user.getActions().forEach(action -> {
            if (action.getCreatedDateTime().plusDays(1).isBefore(currentDate)) {
                action.setActive(false);
            }
        });
        return user.getActions();
    }
    public List<Action> getActionsWithDate(String email,String dateString){
            var user = userService.getUserByEmail(email);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate parsedDate = LocalDate.parse(dateString, formatter);

        return user.getActions().stream()
                .filter(action -> action.getCreatedDateTime().toLocalDate().isEqual(parsedDate))
                .collect(Collectors.toList());
        }


    public Action createAction(String email,Action action){
        var user = userService.getUserByEmail(email);
        var action1 = new Action(action.getPhotographName(),action.getImageUrl(), LocalDateTime.now(),true,user);
        user.addAction(action1);
        return actionRepository.save(action1);
    }
    public Action deactivateAction(String id){
        var action1 = getActionById(id);
        if(action1.isActive()){
            action1.setActive(false);
            return actionRepository.save(action1);
        }
        return action1;
    }
    public Action activateAction(String id){
        var action1 = getActionById(id);
        if(!action1.isActive()){
            action1.setActive(true);
            return actionRepository.save(action1);
        }
        return action1;
    }


    public void deleteAction(String id){
        var action = getActionById(id);
        if(!action.isActive()){
            actionRepository.delete(action);
        }throw new IllegalStateException("Action is active");
    }
}
