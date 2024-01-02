package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserLoginRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.utils.InvalidPasswordException;
import com.kenzie.appserver.utils.UserConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.kenzie.appserver.utils.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserResponse findById(String userId) {
    return UserConverter.recordToResponse(userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " does not exist")));
  }

  public List<UserResponse> getAllUsers() {
    List<UserRecord> records = new ArrayList<>();
    userRepository.findAll().forEach(records::add);
    return records.stream()
            .map(UserResponse::new)
            .collect(Collectors.toList());
  }

  public UserResponse createUser(CreateUserRequest createUserRequest) {
    if(createUserRequest == null){
      throw new IllegalArgumentException("Request was null");
    }
    isUsernameUnique(createUserRequest.getUsername());

    UserRecord userRecord = UserConverter.createRequestToUserRecord(createUserRequest);
    userRecord = userRepository.save(userRecord);
    return new UserResponse(userRecord);
  }

  public void deleteUser(String userId) {
    Optional<UserRecord> record = userRepository.findById(userId);
    if (record.isEmpty()) {
      throw new IllegalArgumentException("User does not exist with given ID: " + userId);
    }
    userRepository.deleteById(record.get().getUserId());
  }

  public UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest) {
    UserRecord record = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("User does not exist with given userId: " + userId));

    User user = userFromUserRecord(record);

    if(userUpdateRequest.getUserId() != null){
      user.setUserId(userUpdateRequest.getUserId());
    }
    if (userUpdateRequest.getUsername() != null && isUsernameUnique(userUpdateRequest.getUsername())) {
      user.setUsername(userUpdateRequest.getUsername());
    }
    if (userUpdateRequest.getPassword() != null) {
      user.setPassword(userUpdateRequest.getPassword());
    }
    if (userUpdateRequest.getFirstName() != null) {
      user.setFirstName(userUpdateRequest.getFirstName());
    }
    if (userUpdateRequest.getLastName() != null) {
      user.setLastName(userUpdateRequest.getLastName());
    }
    if (userUpdateRequest.getAddress() != null) {
      user.setAddress(userUpdateRequest.getAddress());
    }
    if (userUpdateRequest.getPhoneNum() != null) {
      user.setPhoneNum(userUpdateRequest.getPhoneNum());
    }
    if (userUpdateRequest.getEmail() != null) {
      user.setEmail(userUpdateRequest.getEmail());
    }
    if (userUpdateRequest.getDateJoined() != null) {
      user.setDateJoined(userUpdateRequest.getDateJoined());
    }
    if (userUpdateRequest.getUserScheduleIds() != null) {
      user.setUserScheduleIds(userUpdateRequest.getUserScheduleIds());
    }

    UserRecord updatedUserRecord = userRepository.save(userRecordFromUser(user));
    return new UserResponse(updatedUserRecord);
  }

  private boolean isUsernameUnique(String username) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("User already exists with username: " + username);
    }
    return true;
  }

  public UserResponse validateUser(UserLoginRequest loginRequest) {
    UserRecord userRecord = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginRequest.getUsername()));

    if (!userRecord.getPassword().equals(loginRequest.getPassword())) {
      throw new InvalidPasswordException("The password provided does not match the password for username: " + loginRequest.getUsername());
    }
    return new UserResponse(userRecord);
  }

  public User userFromUserRecord(UserRecord record) {
    User user = new User();
    user.setUserId(record.getUserId());
    user.setUsername(record.getUsername());
    user.setPassword(record.getPassword());
    user.setFirstName(record.getFirstName());
    user.setLastName(record.getLastName());
    user.setAddress(record.getAddress());
    user.setPhoneNum(record.getPhoneNum());
    user.setEmail(record.getEmail());
    user.setDateJoined(record.getDateJoined());
    user.setUserScheduleIds(record.getUserScheduleIds());
    return user;
  }

  public UserRecord userRecordFromUser(User user) {
    UserRecord record = new UserRecord();
    record.setUserId(user.getUserId());
    record.setUsername(user.getUsername());
    record.setPassword(user.getPassword());
    record.setFirstName(user.getFirstName());
    record.setLastName(user.getLastName());
    record.setAddress(user.getAddress());
    record.setPhoneNum(user.getPhoneNum());
    record.setEmail(user.getEmail());
    record.setDateJoined(user.getDateJoined());
    record.setUserScheduleIds(user.getUserScheduleIds());
    return record;
  }

  public User userFromResponse(UserResponse response) {
    User user = new User();
    user.setUserId(response.getUserId());
    user.setUsername(response.getUsername());
    user.setPassword(response.getPassword());
    user.setFirstName(response.getFirstName());
    user.setLastName(response.getLastName());
    user.setAddress(response.getAddress());
    user.setPhoneNum(response.getPhoneNum());
    user.setEmail(response.getEmail());
    user.setDateJoined(response.getDateJoined());
    user.setUserScheduleIds(response.getUserScheduleIds());
    return user;
  }

}
