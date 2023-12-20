package com.kenzie.appserver.utils;

import com.kenzie.appserver.controller.model.CreateUserScheduleRequest;
import com.kenzie.appserver.controller.model.UserScheduleResponse;
import com.kenzie.appserver.repositories.model.UserScheduleRecord;

import java.util.UUID;

public class UserScheduleConverter {

    public static UserScheduleRecord convertToRecordFromResponse(UserScheduleResponse response) {
        UserScheduleRecord record = new UserScheduleRecord();
        record.setScheduleId(response.getScheduleId());
        record.setUserId(response.getUserId());
        record.setStart(response.getStart());
        record.setEnd(response.getEnd());
        record.setScheduledEventIds(response.getScheduledEventIds());

        return record;
    }


}
