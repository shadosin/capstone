package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CreateUserScheduleRequest;
import com.kenzie.appserver.controller.model.UserScheduleResponse;
import com.kenzie.appserver.controller.model.UserScheduleUpdateRequest;
import com.kenzie.appserver.service.UserScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;


@IntegrationTest
public class UserScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserScheduleService userScheduleService;

    @Test
    public void findByIdTest() throws Exception {
        String scheduleId = "123";
        UserScheduleResponse mockResponse = new UserScheduleResponse();
        mockResponse.setScheduleId(scheduleId);

        given(userScheduleService.findById(scheduleId)).willReturn(mockResponse);

        mockMvc.perform(get("/user-schedules/" + scheduleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId").value(scheduleId));
    }

    @Test
    public void createUserScheduleTest() throws Exception {
        CreateUserScheduleRequest request = new CreateUserScheduleRequest();
        UserScheduleResponse mockResponse = new UserScheduleResponse();

        given(userScheduleService.createUserSchedule(request)).willReturn(mockResponse);

        mockMvc.perform(post("/user-schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void findCurrentScheduleTest() throws Exception {
        String userId = "user123";
        UserScheduleResponse mockResponse = new UserScheduleResponse();

        given(userScheduleService.findCurrentSchedule(userId)).willReturn(mockResponse);

        mockMvc.perform(get("/user-schedules/current/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserScheduleByIdTest() throws Exception {
        String scheduleId = "123";

        mockMvc.perform(delete("/user-schedules/" + scheduleId))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserScheduleTest() throws Exception {
        String scheduleId = "123";
        UserScheduleUpdateRequest request = new UserScheduleUpdateRequest();
        UserScheduleResponse mockResponse = new UserScheduleResponse();

        given(userScheduleService.updateUserSchedule(scheduleId, request)).willReturn(mockResponse);

        mockMvc.perform(put("/user-schedules/" + scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    // Utility method to convert an object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
