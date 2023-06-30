package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class MeetingRoom {

    @Id
    private String id;

    public UserEntity userEntity;
    public LocalDateTime meetingTime;
    public boolean availability;
    public Booking booking;

}
