package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.repositories.model.UserRecord;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    @NotEmpty
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("address")
    private String address;
    @JsonProperty("phoneNum")
    private String phoneNum;
    @JsonProperty("email")
    private String email;
    @JsonProperty("dateJoined")
    private String dateJoined;

    public UserResponse() {}

    public UserResponse(UserRecord userRecord) {
        this.userId = userRecord.getUserId();
        this.userName = userRecord.getUsername();
        this.firstName = userRecord.getFirstName();
        this.lastName = userRecord.getLastName();
        this.birthDate = userRecord.getBirthDate();
        this.address = userRecord.getAddress();
        this.phoneNum = userRecord.getPhoneNum();
        this.email = userRecord.getEmail();
        this.dateJoined = userRecord.getDateJoined();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;
    }
}
