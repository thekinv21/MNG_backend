package com.mng.Mng.controller;

import com.mng.Mng.dto.UserDto;
import com.mng.Mng.dto.request.CreateUserRequest;
import com.mng.Mng.dto.response.UserResponse;
import com.mng.Mng.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<UserResponse> getAllUsers(@RequestParam(value = "pageNo", defaultValue = "0", required = false ) int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5" , required = false) int pageSize) {
        UserResponse userResponse = userService.getAllUsers(pageNo, pageSize);
        return ResponseEntity.ok(userResponse);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable(value = "email") String email) {
        var user = modelMapper.map(userService.getUserByEmail(email), UserDto.class);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request) {
        var user = modelMapper.map(userService.createUser(request), UserDto.class);
        return ResponseEntity.ok(user);

    }
}
