package scesi.org.check.event.service;

import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.request.CreateEventRequest;
import scesi.org.check.event.model.request.UpdateEventRequest;

import java.util.List;

public class EventServiceImpl implements IEventService {
    @Override
    public EventEntity getById(Long id) {
        return null;
    }

    @Override
    public EventEntity createEvent(CreateEventRequest request) {
        return null;
    }

    @Override
    public EventEntity updateEvent(UpdateEventRequest request, Long id) {
        return null;
    }

    @Override
    public Boolean deleteEvent(Long id) {
        return null;
    }

    @Override
    public List<EventEntity> getAllEvents() {
        return List.of();
    }
}
