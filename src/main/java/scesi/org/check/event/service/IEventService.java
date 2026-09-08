package scesi.org.check.event.service;

import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.request.CreateEventRequest;
import scesi.org.check.event.model.request.UpdateEventRequest;

import java.util.List;

public interface IEventService {
    EventEntity getById(Long eventId);

    EventEntity createEvent(CreateEventRequest request);

    EventEntity updateEvent(UpdateEventRequest request, Long eventId);

    Boolean deleteEvent(Long eventId);

    List<EventEntity> getAllEvents();
}
