package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class MeetingRoom {
    @Id
    private String id;

    private String roomName;
    private String maxAllowance;
    private List<String> availableResources;
    public boolean availability;
    public Booking booking;

}
