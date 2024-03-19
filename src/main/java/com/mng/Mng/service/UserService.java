package com.mng.Mng.service;
import com.mng.Mng.dto.UserDto;
import com.mng.Mng.dto.request.CreateUserRequest;
import com.mng.Mng.dto.response.UserResponse;
import com.mng.Mng.exception.InvalidInputException;
import com.mng.Mng.exception.UserNotFoundException;
import com.mng.Mng.model.User;
import com.mng.Mng.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserResponse getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<User> users = userRepository.findAll(pageable);
        List<User> userList = users.getContent();
        List<UserDto> content = userList.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        List<UserDto> content = userList.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());
        userResponse.setLast(users.isLast());
        return userResponse;
    }
    protected User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    public User getUserByEmail(String email) {
        return findUserByMail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    protected Optional<User> findUserByMail(String email) {
        return userRepository.findUserByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findUserByMail(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }
    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public User createUser(CreateUserRequest request){
        return new User(
                request.getFirstName(), request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhoneNumber(),
                request.getAuthorities());
    }
    public User createUserFromRequest(CreateUserRequest request){
        if (patternMatches(request.getEmail(), "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            throw new InvalidInputException("Email is not valid");
        }
        if (patternMatches(request.getPhoneNumber(), "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
            throw new InvalidInputException("Phone number is not valid");
        }
        return createUser(request);
    }
    public User createNewUser(CreateUserRequest createUserRequest){
        User user = createUserFromRequest(createUserRequest);
        return userRepository.save(user);

    }

}

    }}
