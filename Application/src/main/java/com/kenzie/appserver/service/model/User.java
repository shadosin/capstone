package com.kenzie.appserver.service.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class User {

    private String userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNum;
    private String email;
    private ZonedDateTime dateJoined;
    private List<String> userScheduleIds;

    public User() {}

    public User(String userId,
                String username,
                String password,
                String firstName,
                String lastName,
                String address,
                String phoneNum,
                String email,
                ZonedDateTime dateJoined,
                List<String> userScheduleIds) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNum = phoneNum;
        this.email = email;
        this.dateJoined = dateJoined;
        this.userScheduleIds = userScheduleIds;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(address, user.address) &&
                Objects.equals(phoneNum, user.phoneNum) &&
                Objects.equals(email, user.email) &&
                Objects.equals(dateJoined, user.dateJoined) &&
                Objects.equals(userScheduleIds, user.userScheduleIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, firstName, lastName, address, phoneNum, email, dateJoined, userScheduleIds);
    }
}
