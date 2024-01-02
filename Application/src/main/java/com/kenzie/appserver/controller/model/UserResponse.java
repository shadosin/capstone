package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.repositories.model.UserRecord;

import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
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

    public UserResponse() {}

    public UserResponse(UserRecord userRecord) {
        this.userId = userRecord.getUserId();
        this.username = userRecord.getUsername();
        this.password = userRecord.getPassword();
        this.firstName = userRecord.getFirstName();
        this.lastName = userRecord.getLastName();
        this.address = userRecord.getAddress();
        this.phoneNum = userRecord.getPhoneNum();
        this.email = userRecord.getEmail();
        this.dateJoined = userRecord.getDateJoined();
        this.userScheduleIds = userRecord.getUserScheduleIds();
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
