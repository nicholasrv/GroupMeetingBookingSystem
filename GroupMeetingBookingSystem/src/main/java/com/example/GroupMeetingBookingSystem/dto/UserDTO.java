package com.example.GroupMeetingBookingSystem.dto;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public String username;
}
