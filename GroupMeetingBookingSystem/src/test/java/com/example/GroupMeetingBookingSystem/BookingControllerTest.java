package com.example.GroupMeetingBookingSystem;

import com.example.GroupMeetingBookingSystem.controller.BookingController;
import com.example.GroupMeetingBookingSystem.controller.MeetingRoomController;
import com.example.GroupMeetingBookingSystem.dto.BookingDTO;
import com.example.GroupMeetingBookingSystem.model.Booking;
import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.model.UserEntity;
import com.example.GroupMeetingBookingSystem.repository.BookingRepository;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import com.example.GroupMeetingBookingSystem.repository.UserRepository;
import com.example.GroupMeetingBookingSystem.service.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class BookingControllerTest {


    @Mock
    private MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    private MeetingRoomController meetingRoomController;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    public void testCreateBooking_SuccessfulBooking() {
        // Arrange
        String userId = "user123";
        String roomId = "room456";
        String email = "user@example.com";
        LocalDateTime startTime = LocalDateTime.of(2023, 7, 5, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 7, 5, 12, 0);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setIdUser(userId);
        bookingDTO.setIdRoom(roomId);
        bookingDTO.setEmail(email);
        bookingDTO.setStartTime(startTime);
        bookingDTO.setEndTime(endTime);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(roomId);
        meetingRoom.setAvailability(true);

        Booking booking = new Booking(startTime, endTime, userEntity, meetingRoom);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setUserEntity(userEntity);
        booking.setMeetingRoom(meetingRoom);

        given(userRepository.findById(userId)).willReturn(Optional.of(userEntity));
        given(meetingRoomRepository.findById(roomId)).willReturn(Optional.of(meetingRoom));
        given(meetingRoomRepository.save(meetingRoom)).willReturn(meetingRoom);
        given(bookingRepository.save(booking)).willReturn(booking);

        // Act
        ResponseEntity<?> response = bookingController.createBooking(bookingDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(booking);
    }

    @Test
    public void testCreateBooking_RoomNotAvailable() {
        // Arrange
        String userId = "user123";
        String roomId = "room456";
        String email = "user@example.com";
        LocalDateTime startTime = LocalDateTime.of(2023, 7, 5, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 7, 5, 12, 0);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setIdUser(userId);
        bookingDTO.setIdRoom(roomId);
        bookingDTO.setEmail(email);
        bookingDTO.setStartTime(startTime);
        bookingDTO.setEndTime(endTime);

        MeetingRoom meetingRoom = new MeetingRoom();
        meetingRoom.setId(roomId);
        meetingRoom.setAvailability(false);

        given(meetingRoomRepository.findById(roomId)).willReturn(Optional.of(meetingRoom));

        // Act
        ResponseEntity<?> response = bookingController.createBooking(bookingDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("The requested Meeting Room is not available.");
    }

    @Test
    public void testGetAllAvailableRooms_RoomsAvailable() {
        // Arrange
        MeetingRoom room1 = new MeetingRoom();
        room1.setAvailability(true);
        MeetingRoom room2 = new MeetingRoom();
        room2.setAvailability(true);

        List<MeetingRoom> rooms = Arrays.asList(room1, room2);
        Mockito.when(meetingRoomRepository.findAll()).thenReturn(rooms);

        // Act
        ResponseEntity<?> response = meetingRoomController.getAllAvailableRooms();

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(rooms);
    }


    @Test
    public void testGetAllAvailableRooms_NoRoomsAvailable() {
        // Arrange
        List<MeetingRoom> rooms = Collections.emptyList();
        given(meetingRoomRepository.findAll()).willReturn(rooms);

        // Act
        ResponseEntity<?> response = meetingRoomController.getAllAvailableRooms();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Sorry, but there are no rooms available.");
    }

}
