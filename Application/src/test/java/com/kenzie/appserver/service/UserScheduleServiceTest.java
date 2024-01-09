package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.repositories.ScheduledEventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.UserScheduleRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.service.model.UserSchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserScheduleServiceTest {

    private UserScheduleRepository userScheduleRepository;
    private UserService userService;
    private ScheduledEventService scheduledEventService;

    private UserScheduleService userScheduleService;

    private final ZonedDateTime START_TIME = ZonedDateTime.now();

    private final ZonedDateTime END_TIME = START_TIME.plusHours(4);

    private ScheduledEventRepository scheduledEventRepository;

    private UserRepository userRepository;


    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        scheduledEventRepository = mock(ScheduledEventRepository.class);
        userScheduleRepository = mock(UserScheduleRepository.class);
        userService = mock(UserService.class);
        scheduledEventService = mock(ScheduledEventService.class);
        userScheduleService = new UserScheduleService(userScheduleRepository, userService);
        userScheduleService.setScheduledEventService(scheduledEventService);
    }
    @Test
    public void findById_validId_returnsResponse(){
        //GIVEN
        String scheduleId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        List<String> eventIds = new ArrayList<>();
        eventIds.add(UUID.randomUUID().toString());

        UserScheduleRecord userScheduleRecord1 = new UserScheduleRecord();
        userScheduleRecord1.setScheduleId(scheduleId);
        userScheduleRecord1.setStart(START_TIME);
        userScheduleRecord1.setEnd(END_TIME);
        userScheduleRecord1.setScheduledEventIds(eventIds);
        userScheduleRecord1.setUserId(userId);

        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(userScheduleRecord1));

        //WHEN
        Optional<UserScheduleRecord> userScheduleRecord = userScheduleRepository.findById(scheduleId);
        //THEN
        assertEquals(userScheduleRecord1.getScheduleId(), userScheduleRecord.get().getScheduleId());
    }
    @Test
    public void findById_invalidId_throwsIllegalArgumentException(){
        String scheduleId = UUID.randomUUID().toString();

        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        //WHEN
        //THEN
        assertThrows(IllegalArgumentException.class, () -> userScheduleService.findById(scheduleId));

    }
    @Test
    public void createUserSchedule_validRequest_returnsResponse(){
        String userId = UUID.randomUUID().toString();
        CreateUserScheduleRequest request = new CreateUserScheduleRequest();
        request.setUserId(userId);
        request.setStart(START_TIME);
        request.setEnd(END_TIME);
        request.setScheduledEvents(new ArrayList<>());


        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(UserRecord.class)));
        when(userService.findById(userId)).thenReturn(userResponse);

        User user = new User();
        user.setUserId(userId);
        user.setUserScheduleIds(new ArrayList<>());

        UserScheduleRecord scheduleRecord = new UserScheduleRecord();
        scheduleRecord.setUserId(userId);
        scheduleRecord.setStart(request.getStart());
        scheduleRecord.setEnd(request.getEnd());


        when(userService.userFromResponse(userResponse)).thenReturn(user);

        when(userScheduleRepository.save(any(UserScheduleRecord.class))).thenReturn(scheduleRecord);



        UserScheduleResponse response = userScheduleService.createUserSchedule(request);

        // Assert
        assertNotNull(response);
        assertEquals(request.getUserId(), response.getUserId());
        assertEquals(request.getStart(), response.getStart());
        assertEquals(request.getEnd(), response.getEnd());
    }

    @Test
    public void createUserSchedule_nullRequest_throwsIllegalArgumentException(){
        CreateUserScheduleRequest request = null;
        assertThrows(IllegalArgumentException.class, () -> userScheduleService.createUserSchedule(request));
    }
    @Test
    public void createUserSchedule_idDoesNotExist_throwsIllegalArgumentException(){
        String userId = "123";

        CreateUserScheduleRequest request = new CreateUserScheduleRequest();
        request.setUserId(userId);
        request.setStart(START_TIME);
        request.setEnd(END_TIME);
        request.setScheduledEvents(new ArrayList<>());

        when(userService.findById(userId)).thenThrow(IllegalArgumentException.class);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> userScheduleService.createUserSchedule(request));
        verifyZeroInteractions(scheduledEventService);
    }
    @Test
    public void findCurrentSchedule_validInfo_returnsUserScheduleResponse(){
        String userId = UUID.randomUUID().toString();
        String scheduleId = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);
        userResponse.setUserScheduleIds(Arrays.asList(scheduleId));

        UserScheduleRecord scheduleRecord = new UserScheduleRecord();
        scheduleRecord.setScheduleId(scheduleId);
        scheduleRecord.setUserId(userId);
        scheduleRecord.setStart(now.minusHours(1)); // Schedule started an hour ago
        scheduleRecord.setEnd(now.plusHours(1)); // Schedule ends in an hour

        when(userService.findById(userId)).thenReturn(userResponse);
        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(scheduleRecord));

        UserScheduleResponse result = userScheduleService.findCurrentSchedule(userId);

        // Assert
        assertNotNull(result);
        assertEquals(scheduleId, result.getScheduleId());
        assertEquals(userId, result.getUserId());
        assertTrue(result.getStart().isBefore(now));
        assertTrue(result.getEnd().isAfter(now));
    }
    @Test
    public void findCurrentSchedule_invalidUserId_throwsIllegalArgumentException(){
        //GIVEN
        String userId = UUID.randomUUID().toString();
        //WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //THEN
        assertThrows(NullPointerException.class, () -> when(userScheduleService.findCurrentSchedule(userId)));
    }
    @Test
    public void findCurrentSchedule_emptyUserSchedule_throwsIllegalArgumentException(){
        String userId = UUID.randomUUID().toString();


        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);
        userResponse.setUserScheduleIds(null);

        when(userService.findById(userId)).thenReturn(userResponse);

        assertThrows(IllegalArgumentException.class, () -> userScheduleService.findCurrentSchedule(userId));

    }
    @Test
    public void findCurrentSchedule_emptyResponse_throwsIllegalArgumentException(){
        String userId = UUID.randomUUID().toString();
        String scheduleId = UUID.randomUUID().toString();
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);
        userResponse.setUserScheduleIds(Collections.EMPTY_LIST);

        when(userService.findById(userId)).thenReturn(userResponse);
        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userScheduleService.findCurrentSchedule(userId));

    }
    @Test
    public void deleteUserScheduleById_scheduleExist_scheduleDeleted(){
        String scheduleId = "mock-schedule-id";
        String userId = "mock-user-id";
        List<String> userScheduleIds = new ArrayList<>();
        userScheduleIds.add(scheduleId);

        User mockUser = new User();
        mockUser.setUserId(userId);
        mockUser.setUserScheduleIds(userScheduleIds); // Initialize with non-null list

        UserScheduleRecord mockScheduleRecord = new UserScheduleRecord();

        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(mockScheduleRecord));
        when(userService.userFromResponse(userService.findById(userId))).thenReturn(mockUser);

        userScheduleService.deleteUserScheduleById(scheduleId);

        // Assert
        verify(userScheduleRepository).deleteById(scheduleId);
        assertFalse(mockUser.getUserScheduleIds().contains(scheduleId)); // Verify that the ID was removed
    }
    @Test
    public void updateUserSchedule_validInfo_scheduleUpdated(){
        //GIVEN
        String scheduleId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        ScheduledEventUpdateRequest scheduledEventUpdateRequest = mock(ScheduledEventUpdateRequest.class);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);

        UserSchedule schedule = new UserSchedule();
        schedule.setUserId(userResponse.getUserId());

        UserScheduleUpdateRequest userScheduleUpdateRequest = new UserScheduleUpdateRequest();
        userScheduleUpdateRequest.setScheduleId(scheduleId);
        userScheduleUpdateRequest.setUserId(userId);
        userScheduleUpdateRequest.setStart(START_TIME);
        userScheduleUpdateRequest.setEnd(END_TIME);


        userScheduleUpdateRequest.setScheduledEventUpdates(List.of(scheduledEventUpdateRequest));

        when(userScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(mock(UserScheduleRecord.class)));
        when(userService.findById(userId)).thenReturn(userResponse);
        when(scheduledEventService.updateScheduledEvent(
                scheduledEventUpdateRequest.getEventId(),
                scheduledEventUpdateRequest)).thenReturn(mock(ScheduledEventResponse.class)
                );
        when(userScheduleRepository.save(any(UserScheduleRecord.class))).thenReturn(mock(UserScheduleRecord.class));

        //WHEN
        UserScheduleResponse response = userScheduleService.updateUserSchedule(scheduleId, userScheduleUpdateRequest);

        //THEN
        assertNotNull(response);
        verify(scheduledEventService, times(1)).updateScheduledEvent(
                anyString(),
                any(ScheduledEventUpdateRequest.class)
        );
        verify(userScheduleRepository, times(1)).save(any(UserScheduleRecord.class));

    }
//    @Test
//    public void updateUserSchedule_invalidScheduleId_throwsIllegalArgumentException(){
//        //GIVEN
//        //WHEN
//        //THEN
//    }
}