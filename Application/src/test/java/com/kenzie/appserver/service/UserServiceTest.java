package com.kenzie.appserver.service;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserLoginRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.utils.InvalidPasswordException;
import com.kenzie.appserver.utils.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceTest {

  private UserRepository userRepository;

  private UserService userService;

  
  @BeforeEach
  public void setUp() {
      userRepository = mock(UserRepository.class);
      userService = new UserService(userRepository);
  }

  @Test
  void shouldFindUserById() {
    // GIVEN
    String testUserId = randomUUID().toString();
    UserRecord testUserRecord = new UserRecord();
    testUserRecord.setUserId(testUserId);

    // WHEN
    when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUserRecord));
    UserResponse user = userService.findById(testUserId);

    // THEN
    Assertions.assertNotNull(user, "User record should not be null");
    Assertions.assertEquals(
        testUserRecord.getUserId(),
        user.getUserId(),
        "Returned User ID should match the test User ID");
        }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // GIVEN
        List<UserRecord> testUserRecords = new ArrayList<>();
        testUserRecords.add(new UserRecord());
        testUserRecords.add(new UserRecord());

        // WHEN
        when(userRepository.findAll()).thenReturn(testUserRecords);
        List<UserResponse> users = userService.getAllUsers();

        // THEN
        assertNotNull(users, "User list should not be null");
        assertEquals(testUserRecords.size(), users.size(), "Returned user list size should match test user list size");
    }

    @Test
    void createUser_shouldReturnResponse() {
        // GIVEN
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("testUser");
        createUserRequest.setPassword("testPassword");

        UserRecord createdUserRecord = new UserRecord();
        createdUserRecord.setUserId("createdUserId");
        createdUserRecord.setUsername("testUser");

        // WHEN
        when(userRepository.findByUsername(createUserRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(createdUserRecord);

        UserResponse createdUser = userService.createUser(createUserRequest);

        // THEN
        assertNotNull(createdUser, "Created user should not be null");
        assertEquals(createdUserRecord.getUserId(), createdUser.getUserId(), "Returned User ID should match created User ID");
    }

    @Test
    void createUser_shouldThrowException() {
        // GIVEN
        CreateUserRequest nullCreateUserRequest = null;

        // THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(nullCreateUserRequest),
                "Creating a user with null request should throw an exception");
    }

    @Test
    void createUser_shouldThrowExceptionWhenUsernameExists() {
        // GIVEN
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("existingUser");
        createUserRequest.setPassword("testPassword");

        // WHEN
        when(userRepository.findByUsername(createUserRequest.getUsername())).thenReturn(Optional.of(new UserRecord()));

        // THEN
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(createUserRequest));
    }

    @Test
    void deleteUser_shouldDeleteUser() {
        // GIVEN
        String testUserId = "testUserId";
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUserId(testUserId);

        // WHEN
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUserRecord));
        doNothing().when(userRepository).deleteById(testUserId);

        // THEN
        assertDoesNotThrow(() -> userService.deleteUser(testUserId),
                "Delete operation should not throw an exception");
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenDeletingNonexistentUser() {
        // GIVEN
        String nonExistentUserId = "nonExistentUserId";

        // WHEN
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(nonExistentUserId),
                "Deleting a non-existent user should throw an exception");
    }

    @Test
    void validateUser_shouldReturnResponse() {
        // GIVEN
        String testUsername = "testUser";
        String testPassword = "testPassword";
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUsername(testUsername);
        testUserRecord.setPassword(testPassword);

        UserLoginRequest loginRequest = new UserLoginRequest(testUsername, testPassword);

        // WHEN
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(testUserRecord));

        // THEN
        assertDoesNotThrow(() -> userService.validateUser(loginRequest),
                "Validation should not throw an exception");
    }

    @Test
    void shouldThrowExceptionForInvalidPassword() {
        // GIVEN
        String testUsername = "testUser";
        String correctPassword = "correctPassword";
        String incorrectPassword = "incorrectPassword";
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUsername(testUsername);
        testUserRecord.setPassword(correctPassword);

        UserLoginRequest loginRequest = new UserLoginRequest(testUsername, incorrectPassword);

        // WHEN
        when(userRepository.findByUsername(testUsername)).thenReturn(Optional.of(testUserRecord));

        // THEN
        assertThrows(InvalidPasswordException.class,
                () -> userService.validateUser(loginRequest),
                "Invalid password should throw an exception");
    }

    @Test
    void shouldThrowExceptionForNonexistentUser() {
        // GIVEN
        String nonExistentUsername = "nonExistentUser";
        UserLoginRequest loginRequest = new UserLoginRequest(nonExistentUsername, "anyPassword");

        // WHEN
        when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        // THEN
        assertThrows(UserNotFoundException.class,
                () -> userService.validateUser(loginRequest),
                "Validating a non-existent user should throw an exception");
    }

    @Test
    void userRecordToUser_shouldConvert() {
        // GIVEN
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUserId("testUserId");
        testUserRecord.setUsername("testUser");
        testUserRecord.setPassword("testPassword");
        testUserRecord.setFirstName("Indiana");
        testUserRecord.setLastName("Jones");

        // WHEN
        User convertedUser = userService.userFromUserRecord(testUserRecord);

        // THEN
        assertNotNull(convertedUser, "Converted user should not be null");
        assertEquals(testUserRecord.getUserId(), convertedUser.getUserId(), "User ID should match");
        assertEquals(testUserRecord.getUsername(), convertedUser.getUsername(), "Username should match");
        assertEquals(testUserRecord.getPassword(), convertedUser.getPassword(), "Password should match");
        assertEquals(testUserRecord.getFirstName(), convertedUser.getFirstName(), "First name should match");
        assertEquals(testUserRecord.getLastName(), convertedUser.getLastName(), "Last name should match");
    }

    @Test
    void userToUserRecord_shouldConvert() {
        // GIVEN
        User testUser = new User();
        testUser.setUserId("testUserId");
        testUser.setUsername("testUser");
        testUser.setPassword("testPassword");
        testUser.setFirstName("Indiana");
        testUser.setLastName("Jones");

        // WHEN
        UserRecord convertedUserRecord = userService.userRecordFromUser(testUser);

        // THEN
        assertNotNull(convertedUserRecord, "Converted user record should not be null");
        assertEquals(testUser.getUserId(), convertedUserRecord.getUserId(), "User ID should match");
        assertEquals(testUser.getUsername(), convertedUserRecord.getUsername(), "Username should match");
        assertEquals(testUser.getPassword(), convertedUserRecord.getPassword(), "Password should match");
        assertEquals(testUser.getFirstName(), convertedUserRecord.getFirstName(), "First name should match");
        assertEquals(testUser.getLastName(), convertedUserRecord.getLastName(), "Last name should match");
    }

    @Test
    void UserResponseToUser_shouldConvert() {
        // GIVEN
        UserResponse testUserResponse = new UserResponse();
        testUserResponse.setUserId("testUserId");
        testUserResponse.setUsername("testUser");
        testUserResponse.setPassword("testPassword");
        testUserResponse.setFirstName("Indiana");
        testUserResponse.setLastName("Jones");

        // WHEN
        User convertedUser = userService.userFromResponse(testUserResponse);

        // THEN
        assertNotNull(convertedUser, "Converted user should not be null");
        assertEquals(testUserResponse.getUserId(), convertedUser.getUserId(), "User ID should match");
        assertEquals(testUserResponse.getUsername(), convertedUser.getUsername(), "Username should match");
        assertEquals(testUserResponse.getPassword(), convertedUser.getPassword(), "Password should match");
        assertEquals(testUserResponse.getFirstName(), convertedUser.getFirstName(), "First name should match");
        assertEquals(testUserResponse.getLastName(), convertedUser.getLastName(), "Last name should match");
    }

    @Test
    void updateUser_shouldUpdateUser() {
        // GIVEN
        String testUserId = "testUserId";
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUserId(testUserId);

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUserId(testUserId);
        updateRequest.setUsername("newUsername");
        updateRequest.setPassword("newPassword");
        updateRequest.setFirstName("newFirstName");
        updateRequest.setLastName("newLastName");
        updateRequest.setAddress("newAddress");
        updateRequest.setPhoneNum("newPhoneNum");
        updateRequest.setEmail("newEmail");
        updateRequest.setDateJoined(ZonedDateTime.now());
        updateRequest.setUserScheduleIds(Arrays.asList("id1", "id2"));

        // WHEN
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUserRecord));
        when(userRepository.findByUsername(updateRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(testUserRecord);

        // THEN
        UserResponse updatedUser = userService.updateUser(testUserId, updateRequest);
        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(testUserRecord.getUserId(), updatedUser.getUserId(), "Returned User ID should match test User ID");
    }

    @Test
    void updateUser_shouldUpdateUsernameWhenUnique() {
        // GIVEN
        String testUserId = "testUserId";
        UserRecord testUserRecord = new UserRecord();
        testUserRecord.setUserId(testUserId);
        testUserRecord.setUsername("existingUsername");

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUserId(testUserId);
        updateRequest.setUsername("newUniqueUsername");

        // WHEN
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUserRecord));
        when(userRepository.findByUsername(updateRequest.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(testUserRecord);

        // THEN
        UserResponse updatedUser = userService.updateUser(testUserId, updateRequest);
        assertNotNull(updatedUser, "Updated user should not be null");
        assertEquals(testUserRecord.getUserId(), updatedUser.getUserId(), "Returned User ID should match test User ID");
    }
}