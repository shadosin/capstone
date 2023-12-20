package com.kenzie.appserver.utils;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.model.UserRecord;

import java.util.UUID;

public class UserConverter {

  private UserConverter() {

  }
  public static UserRecord responseToRecord(UserResponse userResponse) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userResponse.getUserName());
    userRecord.setEmail(userResponse.getEmail());
    userRecord.setDateJoined(userResponse.getDateJoined());
    userRecord.setAddress(userResponse.getAddress());
    userRecord.setFirstName(userResponse.getFirstName());
    userRecord.setLastName(userResponse.getLastName());
    userRecord.setUserId(userResponse.getUserId());
    userRecord.setPhoneNum(userResponse.getPhoneNum());
    userRecord.setUserScheduleIds(userResponse.getUserScheduleIds());
    return userRecord;
  }

  public static UserResponse recordToResponse(UserRecord userRecord) {
    UserResponse userResponse = new UserResponse();
    userResponse.setUserName(userRecord.getUsername());
    userResponse.setEmail(userRecord.getEmail());
    userResponse.setDateJoined(userRecord.getDateJoined());
    userResponse.setAddress(userRecord.getAddress());
    userResponse.setFirstName(userRecord.getFirstName());
    userResponse.setLastName(userRecord.getLastName());
    userResponse.setUserId(userRecord.getUserId());
    userResponse.setPhoneNum(userRecord.getPhoneNum());
    userResponse.setUserScheduleIds(userRecord.getUserScheduleIds());
    return userResponse;
  }

  public static UserRecord requestToUserRecord(CreateUserRequest createUserRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUserId(UUID.randomUUID().toString());
    userRecord.setUsername(createUserRequest.getUserName());
    userRecord.setEmail(createUserRequest.getEmail());
    userRecord.setDateJoined(createUserRequest.getDateJoined());
    userRecord.setAddress(createUserRequest.getAddress());
    userRecord.setFirstName(createUserRequest.getFirstName());
    userRecord.setLastName(createUserRequest.getLastName());
    userRecord.setPhoneNum(createUserRequest.getPhoneNum());
    userRecord.setUserScheduleIds(createUserRequest.getUserScheduleIds());
    return userRecord;
  }

  public static UserRecord updateRequestToUserRecord(UserUpdateRequest userUpdateRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userUpdateRequest.getUserName());
    userRecord.setEmail(userUpdateRequest.getEmail());
    userRecord.setDateJoined(userUpdateRequest.getDateJoined());
    userRecord.setAddress(userUpdateRequest.getAddress());
    userRecord.setFirstName(userUpdateRequest.getFirstName());
    userRecord.setLastName(userUpdateRequest.getLastName());
    userRecord.setPhoneNum(userUpdateRequest.getPhoneNum());
    userRecord.setUserId(userUpdateRequest.getUserId());
    userRecord.setUserScheduleIds(userUpdateRequest.getUserScheduleIds());
    return userRecord;
  }
}
