package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Booking {
    @Id
    public String id;

    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public UserEntity userEntity;
}
