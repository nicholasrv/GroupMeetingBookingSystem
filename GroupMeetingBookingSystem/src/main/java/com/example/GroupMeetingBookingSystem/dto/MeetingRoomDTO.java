package com.example.GroupMeetingBookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRoomDTO {
    private String roomName;
    private String maxAllowance;
    private List<String> availableResources;
    public boolean availability;
}
