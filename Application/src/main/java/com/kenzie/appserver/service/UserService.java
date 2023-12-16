package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
    this.userRepository = userRepository;
  }

  public UserRecord findById(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public List<UserRecord> getAllUsers() {
    List<UserRecord> allUsers = new ArrayList<>();

    userRepository.findAll().forEach(allUsers::add);
    return allUsers;
  }

  public UserRecord createUser(UserRecord user) {
    return userRepository.save(user);
  }

  public String deleteUser(String userId) {
    try {
      userRepository.deleteById(userId);
      return userId;
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  public UserRecord updateUser(UserRecord user) {
    return userRepository.save(user);
  }

  public Boolean doesUserIdExist(String userId) {
    return userRepository.existsById(userId);
  }
}
