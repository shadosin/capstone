package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.UserService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getUser_Exists() throws Exception {
        String userId = "123";
        UserResponse mockResponse = new UserResponse();
        mockResponse.setUserId(userId);

        given(userService.findById(userId)).willReturn(mockResponse);

        mvc.perform(get("/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    public void getAllUsers_Exists() throws Exception {
        List<UserResponse> responses = new ArrayList<>();

        given(userService.getAllUsers()).willReturn(responses);

        mvc.perform(get("/user/all"))
                .andExpect(status().isOk());
    }

    @Test
    public void createUser_CreateSuccessful() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        UserResponse mockResponse =new UserResponse();

        given(userService.createUser(request)).willReturn(mockResponse);

        mvc.perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteUser_DeleteSuccessful() throws Exception {
        String userId = "user123";

        mvc.perform(delete("/user/" + userId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateUser_PutSuccessful() throws Exception {
        String userId = "123";
        UserUpdateRequest request = new UserUpdateRequest();
        UserResponse mockResponse = new UserResponse();

        given(userService.updateUser(userId, request)).willReturn(mockResponse);

        mvc.perform(put("/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUser_PostSuccessful() throws Exception {
        UserLoginRequest request = new UserLoginRequest();
        UserResponse mockResponse = new UserResponse();

        given(userService.validateUser(request)).willReturn(mockResponse);

        mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
