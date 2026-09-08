package scesi.org.check.event.service;

import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.request.CreateEventRequest;
import scesi.org.check.event.model.request.UpdateEventRequest;

import java.util.List;

public interface IEventService {
    EventEntity getById(Long id);
    EventEntity createEvent(CreateEventRequest request);
    EventEntity updateEvent(UpdateEventRequest request, Long id);
    Boolean deleteEvent(Long id);
    List<EventEntity> getAllEvents();
}
