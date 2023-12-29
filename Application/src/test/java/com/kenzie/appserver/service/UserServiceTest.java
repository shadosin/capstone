package com.kenzie.appserver.service;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import java.util.Optional;
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
  }