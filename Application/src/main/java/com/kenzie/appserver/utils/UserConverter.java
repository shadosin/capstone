package com.kenzie.appserver.utils;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.model.UserRecord;

import java.time.ZonedDateTime;
import java.util.UUID;

public class UserConverter {

  private UserConverter() {

  }
  public static UserRecord responseToRecord(UserResponse userResponse) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userResponse.getUsername());
    userRecord.setPassword(userResponse.getPassword());
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
    userResponse.setUsername(userRecord.getUsername());
    userResponse.setPassword(userRecord.getPassword());
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

  public static UserRecord createRequestToUserRecord(CreateUserRequest createUserRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUserId(UUID.randomUUID().toString()); //sets UUID upon creation
    userRecord.setUsername(createUserRequest.getUsername());
    userRecord.setPassword(createUserRequest.getPassword());
    userRecord.setEmail(createUserRequest.getEmail());
    userRecord.setDateJoined(ZonedDateTime.now()); //sets date upon creation
    userRecord.setAddress(createUserRequest.getAddress());
    userRecord.setFirstName(createUserRequest.getFirstName());
    userRecord.setLastName(createUserRequest.getLastName());
    userRecord.setPhoneNum(createUserRequest.getPhoneNum());
    userRecord.setUserScheduleIds(createUserRequest.getUserScheduleIds());
    return userRecord;
  }

  public static UserRecord updateRequestToUserRecord(UserUpdateRequest userUpdateRequest) {
    UserRecord userRecord = new UserRecord();
    userRecord.setUsername(userUpdateRequest.getUsername());
    userRecord.setPassword(userUpdateRequest.getPassword());
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
  public static UserUpdateRequest recordToUpdateUserRequest(UserRecord userRecord){
    UserUpdateRequest updateRequest = new UserUpdateRequest();
    updateRequest.setUserId(userRecord.getUserId());
    updateRequest.setUsername(userRecord.getUsername());
    updateRequest.setPassword(userRecord.getPassword());
    updateRequest.setFirstName(userRecord.getFirstName());
    updateRequest.setLastName(userRecord.getLastName());
    updateRequest.setAddress(userRecord.getAddress());
    updateRequest.setPhoneNum(userRecord.getPhoneNum());
    updateRequest.setEmail(userRecord.getEmail());
    updateRequest.setDateJoined(userRecord.getDateJoined());
    updateRequest.setUserScheduleIds(userRecord.getUserScheduleIds());

    return updateRequest;
  }

}
