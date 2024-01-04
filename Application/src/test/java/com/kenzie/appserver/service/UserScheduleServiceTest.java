package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class UserScheduleServiceTest {

    private UserScheduleRepository userScheduleRepository;
    private UserService userService;
    private ScheduledEventService scheduledEventService;

    @BeforeEach
    public void setup() {
        userScheduleRepository = mock(UserScheduleRepository.class);
        userService = mock(UserService.class);
        scheduledEventService = mock(ScheduledEventService.class);
        UserScheduleService userScheduleService = new UserScheduleService(userScheduleRepository, userService);
        userScheduleService.setScheduledEventService(scheduledEventService);
    }
    @Test
    public void findById_validId_returnsResponse(){

    }
    @Test
    public void findById_invalidId_throwsIllegalArgumentException(){

    }
    @Test
    public void createUserSchedule_validRequest_returnsResponse(){

    }
    @Test
    public void createUserSchedule_nullRequest_throwsIllegalArgumentException(){

    }
    @Test
    public void createUserSchedule_idDoesNotExist_throwsIllegalArgumentException(){

    }
    @Test
    public void createUserSchedule_badEventRequest_throwsIllegalArgumentException(){

    }
    @Test
    public void createUserSchedule_nullRecordToSave_throwsIllegalArgumentException(){

    }
    @Test
    public void findCurrentSchedule_validInfo_returnsUserScheduleResponse(){

    }
    @Test
    public void findCurrentSchedule_invalidUserId_throwsIllegalArgumentException(){

    }
    @Test
    public void findCurrentSchedule_emptyUserScheduleId_throwsIllegalArgumentException(){

    }
    @Test
    public void deleteUserScheduleById_invalidScheduleId_throwsIllegalArgumentException(){

    }
    @Test
    public void deleteUserScheduleById_userDoesNotExist_throwsIllegalArgumentException(){

    }
    @Test
    public void updateUserSchedule_validInfo_scheduleUpdated(){

    }
    @Test
    public void updateUserSchedule_invalidScheduleId_throwsIllegalArgumentException(){

    }

}
