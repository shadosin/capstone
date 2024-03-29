package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.User;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

public class UserUpdateRequest {
    @NotEmpty
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phoneNum")
    private String phoneNum;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dateJoined")
    private ZonedDateTime dateJoined;
    @JsonProperty("scheduleIdList")
    private List<String> userScheduleIds;

    public UserUpdateRequest() {}

    public UserUpdateRequest(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.phoneNum = user.getPhoneNum();
        this.email = user.getEmail();
        this.dateJoined = user.getDateJoined();
        this.userScheduleIds = user.getUserScheduleIds();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(ZonedDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<String> getUserScheduleIds() {
        return userScheduleIds;
    }

    public void setUserScheduleIds(List<String> userScheduleIds) {
        this.userScheduleIds = userScheduleIds;
    }
}
