package com.example.GroupMeetingBookingSystem.service;

import java.util.List;
import java.util.Optional;

public interface MeetingBookingSystem <T>{

        public T save (T t);

        public String update (T t);

        public List<T> getAllResults();

        public Optional<T> searchById(String id);

        public boolean delete(String id);

}

