package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CreateUserRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.controller.model.UserUpdateRequest;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.utils.UserConverter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}/")
  public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
      UserRecord userRecord = userService.findById(userId);
      UserResponse userResponse = UserConverter.recordToResponse(userRecord);
      return ResponseEntity.ok(userResponse);
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserResponse>> getAllUsers() {
      List<UserResponse> userList =
          userService.getAllUsers().stream()
              .map(UserConverter::recordToResponse)
              .collect(Collectors.toList());
      return ResponseEntity.ok(userList);
  }

  @PostMapping("/create")
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
      UserResponse response = userService.createUser(createUserRequest);
      return ResponseEntity.ok().body(response);

  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
      userService.deleteUser(userId);
      return ResponseEntity.noContent().build();
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable("userId") String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
      UserResponse response = userService.updateUser(userId, userUpdateRequest);
      return ResponseEntity.ok(response);
  }
}
