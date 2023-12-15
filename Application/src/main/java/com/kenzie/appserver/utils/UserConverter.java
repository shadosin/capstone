package com.kenzie.appserver.utils;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.model.UserRecord;

public class UserConverter {

  private UserConverter() {

  }
  public static UserRecord responseToRecord(UserResponse userResponse) {

    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userResponse.getUserName());
    userRecord.setEmail(userResponse.getEmail());
    userRecord.setDateJoined(userResponse.getDateJoined());
    userRecord.setAddress(userResponse.getAddress());
    userRecord.setBirthDate(userResponse.getBirthDate());
    userRecord.setFirstName(userResponse.getFirstName());
    userRecord.setLastName(userResponse.getLastName());
    userRecord.setUserId(userResponse.getUserId());
    userRecord.setPhoneNum(userResponse.getPhoneNum());
    return userRecord;
  }

  public static UserResponse recordToResponse(UserRecord userRecord) {
    UserResponse userResponse = new UserResponse();
    userResponse.setUserName(userRecord.getUsername());
    userResponse.setEmail(userRecord.getEmail());
    userResponse.setDateJoined(userRecord.getDateJoined());
    userResponse.setAddress(userRecord.getAddress());
    userResponse.setBirthDate(userRecord.getBirthDate());
    userResponse.setFirstName(userRecord.getFirstName());
    userResponse.setLastName(userRecord.getLastName());
    userResponse.setUserId(userRecord.getUserId());
    userResponse.setPhoneNum(userRecord.getPhoneNum());
    return userResponse;
  }

  public static UserRecord requestToUserRecord(CreateUserRequest createUserRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(createUserRequest.getUserName());
    userRecord.setEmail(createUserRequest.getEmail());
    userRecord.setDateJoined(createUserRequest.getDateJoined());
    userRecord.setAddress(createUserRequest.getAddress());
    userRecord.setBirthDate(createUserRequest.getBirthDate());
    userRecord.setFirstName(createUserRequest.getFirstName());
    userRecord.setLastName(createUserRequest.getLastName());
    userRecord.setPhoneNum(createUserRequest.getPhoneNum());
    return userRecord;
  }

  public static UserRecord requestToUserRecord(UserUpdateRequest userUpdateRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userUpdateRequest.getUserName());
    userRecord.setEmail(userUpdateRequest.getEmail());
    userRecord.setDateJoined(userUpdateRequest.getDateJoined());
    userRecord.setAddress(userUpdateRequest.getAddress());
    userRecord.setBirthDate(userUpdateRequest.getBirthDate());
    userRecord.setFirstName(userUpdateRequest.getFirstName());
    userRecord.setLastName(userUpdateRequest.getLastName());
    userRecord.setPhoneNum(userUpdateRequest.getPhoneNum());
    userRecord.setUserId(userUpdateRequest.getUserId());
    return userRecord;
  }
}
