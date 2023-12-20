package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.utils.UserConverter;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
    this.userRepository = userRepository;
  }

  public UserRecord findById(String userId) {
    return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(
        "User with id " + userId + " does not exist"));
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
    UserRecord userRecord = UserConverter.requestToUserRecord(createUserRequest);
    userRepository.save(userRecord);
    return new UserResponse(userRecord);
  }

  public void deleteUser(String userId) {
    Optional<UserRecord> record = userRepository.findById(userId);
    if (record.isEmpty()) {
      throw new IllegalArgumentException("Event does not exist with given ID");
    }
    userRepository.deleteById(record.get().getUserId());
  }

  public UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest) {
    UserRecord oldRecord = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("User does not exist with given userId: " + userId));
    if(userUpdateRequest.getUserId() != null){
        oldRecord.setUserId(userUpdateRequest.getUserId());
    }
    if (userUpdateRequest.getUserName() != null) {
        oldRecord.setUsername(userUpdateRequest.getUserName());
    }
    if (userUpdateRequest.getFirstName() != null) {
        oldRecord.setFirstName(userUpdateRequest.getFirstName());
    }
    if (userUpdateRequest.getLastName() != null) {
        oldRecord.setLastName(userUpdateRequest.getLastName());
    }
    if (userUpdateRequest.getAddress() != null) {
        oldRecord.setAddress(userUpdateRequest.getAddress());
    }
    if (userUpdateRequest.getPhoneNum() != null) {
        oldRecord.setPhoneNum(userUpdateRequest.getPhoneNum());
    }
    if (userUpdateRequest.getEmail() != null) {
        oldRecord.setEmail(userUpdateRequest.getEmail());
    }

    UserRecord updatedUserRecord = userRepository.save(oldRecord);
    return new UserResponse(updatedUserRecord);
  }

  public Boolean doesUserIdExist(String userId) {
    return userRepository.existsById(userId);
  }
}
