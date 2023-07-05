package com.example.GroupMeetingBookingSystem;

import com.example.GroupMeetingBookingSystem.model.Role;
import com.example.GroupMeetingBookingSystem.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GroupMeetingBookingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupMeetingBookingSystemApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner (RoleRepository repository) {
//		return args -> {
//			Role role = new Role("USER");
//			Role role1 = new Role("ADMIN");
//			repository.save(role);
//			repository.save(role1);
//		};
//	}

}
