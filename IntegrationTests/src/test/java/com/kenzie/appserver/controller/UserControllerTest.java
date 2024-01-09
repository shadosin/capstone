package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.UserService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    UserService userService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getUser_Exists() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(mockNeat.strings().val());
        createUserRequest.setPassword(mockNeat.strings().val());
        UserResponse createdUser = userService.createUser(createUserRequest);

        mvc.perform(get("/user/{userId}", createdUser.getUserId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(createdUser.getUserId()))
                .andExpect(jsonPath("username").value(createdUser.getUsername()));
    }

    @Test
    public void getAllUsers_Exists() throws Exception {
        mvc.perform(get("/user/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_CreateSuccessful() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(mockNeat.strings().val());
        createUserRequest.setPassword(mockNeat.strings().val());

        String requestJson = mapper.writeValueAsString(createUserRequest);

        mvc.perform(post("/user/create")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").isNotEmpty());
    }

    @Test
    public void deleteUser_DeleteSuccessful() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(mockNeat.strings().val());
        createUserRequest.setPassword(mockNeat.strings().val());
        UserResponse userResponse = userService.createUser(createUserRequest);

        mvc.perform(delete("/user/{userId}", userResponse.getUserId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_PutSuccessful() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(mockNeat.strings().val());
        createUserRequest.setPassword(mockNeat.strings().val());
        UserResponse userResponse = userService.createUser(createUserRequest);

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setUsername(mockNeat.strings().val());

        String requestJson = mapper.writeValueAsString(updateRequest);

        mvc.perform(put("/user/{userId}", userResponse.getUserId())
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(userResponse.getUserId()))
                .andExpect(jsonPath("username").value(updateRequest.getUsername()));
    }

    @Test
    public void loginUser_PostSuccessful() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername(mockNeat.strings().val());
        createUserRequest.setPassword("password");
        UserResponse userResponse = userService.createUser(createUserRequest);

        // Create a login request
        UserLoginRequest loginRequest = new UserLoginRequest();
        loginRequest.setUsername(userResponse.getUsername());
        loginRequest.setPassword("password");

        String requestJson = mapper.writeValueAsString(loginRequest);

        mvc.perform(post("/user/login")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(userResponse.getUserId()))
                .andExpect(jsonPath("username").value(userResponse.getUsername()));
    }

}
