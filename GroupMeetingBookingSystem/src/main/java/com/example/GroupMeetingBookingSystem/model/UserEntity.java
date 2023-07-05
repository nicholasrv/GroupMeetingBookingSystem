package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class UserEntity {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    @Indexed(unique = true)
    public String email;
    public String username;
    public String password;

    private List<Role> roles = new ArrayList<>();
}
