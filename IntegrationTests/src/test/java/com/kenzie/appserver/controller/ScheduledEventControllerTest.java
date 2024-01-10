package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.controller.model.ScheduledEventResponse;
import com.kenzie.appserver.controller.model.ScheduledEventUpdateRequest;
import com.kenzie.appserver.service.ScheduledEventService;
import com.kenzie.appserver.service.model.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
public class ScheduledEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduledEventService scheduledEventService;

    @Test
    public void createScheduledEventTest() throws Exception {
        CreateScheduledEventRequest request = new CreateScheduledEventRequest();
        request.setUserId("testUserId");
        request.setMealId("mealId");
        request.setEventType(EventType.MEAL);
        request.setScheduledDateTime(ZonedDateTime.now().minusHours(1));
        request.setCompleted(false);
        request.setMetricsCalculated(false);

        ScheduledEventResponse mockResponse = new ScheduledEventResponse();
        mockResponse.setEventId("eventId");
        mockResponse.setUserId("testUserId");
        mockResponse.setMealId("mealId");
        mockResponse.setEventType(EventType.MEAL);
        mockResponse.setScheduledDateTime(ZonedDateTime.now().minusHours(1));
        mockResponse.setCompleted(false);
        mockResponse.setMetricsCalculated(false);

        given(scheduledEventService.createScheduledEvent(request)).willReturn(mockResponse);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateScheduledEventTest() throws Exception {
        String eventId = "testEventId";
        ScheduledEventUpdateRequest request = new ScheduledEventUpdateRequest();
        request.setUserId("testUserId");
        request.setMealId("mealId");
        request.setEventType(EventType.MEAL);
        request.setScheduledDateTime(ZonedDateTime.now().minusHours(1));
        request.setCompleted(false);
        request.setMetricsCalculated(false);

        ScheduledEventResponse mockResponse = new ScheduledEventResponse();
        mockResponse.setEventId(eventId);
        mockResponse.setUserId("testUserId");
        mockResponse.setMealId("mealId");
        mockResponse.setEventType(EventType.MEAL);
        mockResponse.setScheduledDateTime(ZonedDateTime.now().minusHours(1));
        mockResponse.setCompleted(false);
        mockResponse.setMetricsCalculated(false);

        given(scheduledEventService.updateScheduledEvent(eventId, request)).willReturn(mockResponse);

        mockMvc.perform(put("/events/" + eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void getScheduledEventByIdTest() throws Exception {
        String eventId = "testEventId";
        ScheduledEventResponse mockResponse = new ScheduledEventResponse();
        mockResponse.setEventId(eventId);
        mockResponse.setUserId("testUserId");
        mockResponse.setMealId("mealId");
        mockResponse.setEventType(EventType.MEAL);
        mockResponse.setScheduledDateTime(ZonedDateTime.now().minusHours(1));
        mockResponse.setCompleted(false);
        mockResponse.setMetricsCalculated(false);

        given(scheduledEventService.findById(eventId)).willReturn(mockResponse);

        mockMvc.perform(get("/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value(eventId));
    }

    @Test
    public void deleteEventTest() throws Exception {
        String eventId = "testEventId";

        mockMvc.perform(delete("/events/" + eventId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllScheduledEventsTest() throws Exception {
        ScheduledEventResponse eventResponse1 = new ScheduledEventResponse();
        eventResponse1.setEventId("eventId1");
        ScheduledEventResponse eventResponse2 = new ScheduledEventResponse();
        eventResponse2.setEventId("eventId2");

        List<ScheduledEventResponse> mockResponses = Arrays.asList(eventResponse1, eventResponse2);

        given(scheduledEventService.getAllScheduledEvents()).willReturn(mockResponses);

        mockMvc.perform(get("/events/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eventId").value("eventId1"))
                .andExpect(jsonPath("$[1].eventId").value("eventId2"));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
