package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CreateScheduledEventRequest;
import com.kenzie.appserver.controller.model.ScheduledEventResponse;
import com.kenzie.appserver.repositories.ScheduledEventRepository;
import com.kenzie.appserver.repositories.model.ScheduledEventRecord;
import com.kenzie.appserver.service.model.EventType;
import com.kenzie.appserver.service.model.ScheduledEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ScheduleEventServiceTest {

    private ScheduledEventService scheduledEventService;

    @Mock
    private ScheduledEventRepository scheduledEventRepository;

    @Mock
    private EventPublisher eventPublisher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scheduledEventService = new ScheduledEventService(scheduledEventRepository, eventPublisher);
    }

    @Test
    void findById_shouldReturnResponse() {
        // GIVEN
        String eventId = randomUUID().toString();
        ScheduledEventRecord eventRecord = new ScheduledEventRecord();
        eventRecord.setEventId(eventId);

        // WHEN
        when(scheduledEventRepository.findById(eventId)).thenReturn(Optional.of(eventRecord));
        ScheduledEventResponse response = scheduledEventService.findById(eventId);

        // THEN
        assertNotNull(response, "ScheduleEvent record should not be null");
        assertEquals(
                eventRecord.getEventId(),
                response.getEventId(),
                "Returned ScheduleEvent ID should match the test ScheduleEvent ID");
    }

    @Test
    void findById_shouldThrowException() {
        // GIVEN
        String eventId = randomUUID().toString();

        // WHEN
        when(scheduledEventRepository.findById(eventId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(IllegalArgumentException.class, () -> scheduledEventService.findById(eventId));

    }

    @Test
    void createScheduleEvent_shouldReturnResponse() {
        // GIVEN
        CreateScheduledEventRequest request = new CreateScheduledEventRequest();
        request.setUserId(UUID.randomUUID().toString());
        request.setEventType(EventType.MEAL);
        request.setScheduledDateTime(ZonedDateTime.now());


        // WHEN
        ScheduledEventRecord savedRecord = new ScheduledEventRecord();
        when(scheduledEventRepository.save(any(ScheduledEventRecord.class))).thenReturn(savedRecord);
        ScheduledEventResponse response = scheduledEventService.createScheduledEvent(request);

        // THEN
        assertNotNull(response, "ScheduledEvent record should not be null");
        assertEquals(savedRecord.getEventId(), response.getEventId(), "Returned ScheduleEvent ID should match the saved record ID");
    }

    @Test
    void createScheduleEvent_shouldThrowException() {
        // GIVEN
        CreateScheduledEventRequest request = null;

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> scheduledEventService.createScheduledEvent(request));

    }

    @Test
    void deleteScheduledEvent_shouldDeleteEvent() {
        // GIVEN
        String eventId = UUID.randomUUID().toString();

        // WHEN
        when(scheduledEventRepository.findById(eventId)).thenReturn(Optional.of(new ScheduledEventRecord()));
        scheduledEventService.deleteScheduledEvent(eventId);

        // THEN
        verify(scheduledEventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void deleteScheduledEvent_shouldThrowExceptionWhenEventNotFound() {
        // GIVEN
        String eventId = UUID.randomUUID().toString();
        when(scheduledEventRepository.findById(eventId)).thenReturn(Optional.empty());

        // WHEN + THEN
        assertThrows(IllegalArgumentException.class, () -> scheduledEventService.deleteScheduledEvent(eventId));
    }

    @Test
    void getAllScheduledEvents_shouldReturnListOfEvents() {
        // GIVEN
        List<ScheduledEventRecord> eventRecords = Arrays.asList(new ScheduledEventRecord(), new ScheduledEventRecord());
        when(scheduledEventRepository.findAll()).thenReturn(eventRecords);

        // WHEN
        List<ScheduledEventResponse> responses = scheduledEventService.getAllScheduledEvents();

        // THEN
        assertNotNull(responses);
        assertEquals(eventRecords.size(), responses.size());
    }

    @Test
    void eventFromResponse_shouldMapResponseToEvent() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // GIVEN
        ScheduledEventResponse response = new ScheduledEventResponse();
        response.setEventId("testEventId");
        response.setUserId("testUserId");
        response.setMealId("testMealId");
        response.setExerciseId("testExerciseId");
        response.setEventType(EventType.EXERCISE);
        response.setScheduledDateTime(ZonedDateTime.now());
        response.setCompleted(true);
        response.setMetricsCalculated(false);

        // WHEN
        Method method = ScheduledEventService.class.getDeclaredMethod("eventFromResponse", ScheduledEventResponse.class);
        method.setAccessible(true);
        ScheduledEvent event = (ScheduledEvent) method.invoke(scheduledEventService, response);

        // THEN
        assertNotNull(event);
        assertEquals(response.getEventId(), event.getEventId());
        assertEquals(response.getUserId(), event.getUserId());
        assertEquals(response.getMealId(), event.getMealId());
        assertEquals(response.getExerciseId(), event.getExerciseId());
        assertEquals(response.getEventType(), event.getEventType());
        assertEquals(response.getScheduledDateTime(), event.getScheduledDateTime());
        assertEquals(response.isCompleted(), event.isCompleted());
        assertEquals(response.isMetricsCalculated(), event.isMetricsCalculated());
    }

    @Test
    void recordFromScheduledEvent_shouldMapEventToRecord() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // GIVEN
        ScheduledEvent event = new ScheduledEvent();
        event.setEventId("testEventId");
        event.setUserId("testUserId");
        event.setMealId("testMealId");
        event.setExerciseId("testExerciseId");
        event.setEventType(EventType.EXERCISE);
        event.setScheduledDateTime(ZonedDateTime.now());
        event.setCompleted(true);
        event.setMetricsCalculated(false);

        // WHEN
        Method method = ScheduledEventService.class.getDeclaredMethod("recordFromScheduledEvent", ScheduledEvent.class);
        method.setAccessible(true);
        ScheduledEventRecord record = (ScheduledEventRecord) method.invoke(scheduledEventService, event);

        // THEN
        assertNotNull(record);
        assertEquals(event.getEventId(), record.getEventId());
        assertEquals(event.getUserId(), record.getUserId());
        assertEquals(event.getMealId(), record.getMealId());
        assertEquals(event.getExerciseId(), record.getExerciseId());
        assertEquals(event.getEventType(), record.getEventType());
        assertEquals(event.getScheduledDateTime(), record.getScheduledDateTime());
        assertEquals(event.isCompleted(), record.isCompleted());
        assertEquals(event.isMetricsCalculated(), record.isMetricsCalculated());
    }
}
