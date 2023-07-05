package com.example.GroupMeetingBookingSystem.dto;

import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public String email;
    public String idUser;
    public String idRoom;

}
