package com.example.eventmanager.service;

import com.example.eventmanager.client.CategoryServiceClient;
import com.example.eventmanager.client.NotificationServiceClient;
import com.example.eventmanager.client.UserServiceClient;
import com.example.eventmanager.client.VenueServiceClient;
import com.example.eventmanager.dto.UserDto;
import com.example.eventmanager.entity.Event;
import com.example.eventmanager.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserServiceClient userServiceClient;
    private final CategoryServiceClient categoryServiceClient;
    private final VenueServiceClient venueServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    @Autowired
    public EventService(EventRepository eventRepository,
                        UserServiceClient userServiceClient,
                        CategoryServiceClient categoryServiceClient,
                        VenueServiceClient venueServiceClient,
                        NotificationServiceClient notificationServiceClient) {
        this.eventRepository = eventRepository;
        this.userServiceClient = userServiceClient;
        this.categoryServiceClient = categoryServiceClient;
        this.venueServiceClient = venueServiceClient;
        this.notificationServiceClient = notificationServiceClient;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        event.setTitle(eventDetails.getTitle());
        event.setDescription(eventDetails.getDescription());
        event.setEventDate(eventDetails.getEventDate());
        event.setStatus(eventDetails.getStatus());

        event.setCategoryId(eventDetails.getCategoryId());
        event.setVenueId(eventDetails.getVenueId());
        event.setOrganizerId(eventDetails.getOrganizerId());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    public void registerAttendee(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        UserDto attendee = userServiceClient.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getAttendeeIds().contains(userId)) {
            throw new RuntimeException("User is already registered for this event");
        }

        event.getAttendeeIds().add(userId);
        eventRepository.save(event);

        String message = "You have successfully registered for event: " + event.getTitle();
        notificationServiceClient.sendNotification(userId, message);
    }

}
