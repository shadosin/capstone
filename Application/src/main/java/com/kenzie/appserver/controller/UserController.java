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
    try {
      UserRecord userRecord = userService.findById(userId);
      UserResponse userResponse = UserConverter.recordToResponse(userRecord);
      return ResponseEntity.ok(userResponse);
    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<UserResponse>> getAllUsers() {
    try {
      List<UserResponse> userList =
          userService.getAllUsers().stream()
              .map(UserConverter::recordToResponse)
              .collect(Collectors.toList());

      return ResponseEntity.ok(userList);

    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/create")
  public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
    try {
      UserRecord userRecord = UserConverter.requestToUserRecord(createUserRequest);
      UserRecord savedRecord = userService.createUser(userRecord);
      UserResponse userResponse = UserConverter.recordToResponse(savedRecord);
      //      return ResponseEntity.created(URI.create("/user/" +
      // userResponse.getUserId())).body(userResponse);
      return ResponseEntity.ok().body(userResponse);

    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
    try {
      String result = userService.deleteUser(userId);
      return ResponseEntity.ok(result);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{userId}")
  public ResponseEntity<UserResponse> updateUser(
      @PathVariable("userId") String userId, @RequestBody UserUpdateRequest userUpdateRequest) {
    try {
      UserRecord userRecord = UserConverter.requestToUserRecord(userUpdateRequest);
      UserRecord updatedRecord = userService.updateUser(userRecord);
      return ResponseEntity.accepted().body(UserConverter.recordToResponse(updatedRecord));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
