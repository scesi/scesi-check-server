package scesi.org.check.event.service;

import org.springframework.stereotype.Service;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.exception.EventAlreadyExistException;
import scesi.org.check.event.model.exception.EventNotFoundException;
import scesi.org.check.event.model.repository.IEventRepository;
import scesi.org.check.event.model.request.CreateEventRequest;
import scesi.org.check.event.model.request.UpdateEventRequest;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements IEventService {

    private final IEventRepository iEventRepository;

    public EventServiceImpl(IEventRepository iEventRepository) {
        this.iEventRepository = iEventRepository;
    }

    @Override
    public EventEntity getById(Long eventId) {
        Optional<EventEntity> eventEntityOptional = iEventRepository.findById(eventId);
        if (eventEntityOptional.isEmpty()) {
            throw new EventNotFoundException();
        }
        return eventEntityOptional.get();
    }

    @Override
    public EventEntity createEvent(CreateEventRequest request) {
        Optional<EventEntity> eventEntityOptional = iEventRepository.findByTitle(request.title());
        if (eventEntityOptional.isEmpty()) {
            throw new EventNotFoundException();
        }
        EventEntity createEntity = EventEntity.builder()
                .title(request.title())
                .description(request.description())
                .nextControl(request.nextControl())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .build();
        return iEventRepository.save(createEntity);
    }

    @Override
    public EventEntity updateEvent(UpdateEventRequest request, Long eventId) {
        Optional<EventEntity> eventEntityOptional = iEventRepository.findById(eventId);
        if (eventEntityOptional.isEmpty()) {
            throw new EventNotFoundException();
        }
        EventEntity eventToUpdate = eventEntityOptional.get();
        if (request.description() != null) {
            eventToUpdate.setDescription(request.description());
        }
        if (request.endTime() != null) {
            eventToUpdate.setEndTime(request.endTime());
        }
        if (request.startTime() != null) {
            eventToUpdate.setStartTime(request.startTime());
        }
        if (request.nextControl() != null) {
            eventToUpdate.setNextControl(request.nextControl());
        }
        if (request.title() != null) {
            Optional<EventEntity> eventTitleVerification = iEventRepository.findByTitle(request.title());
            if (eventTitleVerification.isPresent()) {
                throw new EventAlreadyExistException();
            }
            eventToUpdate.setTitle(request.title());
        }
        return iEventRepository.save(eventToUpdate);
    }

    @Override
    public Boolean deleteEvent(Long eventId) {
        Optional<EventEntity> eventEntityOptional = iEventRepository.findById(eventId);
        if (eventEntityOptional.isEmpty()) {
            throw new EventNotFoundException();
        }
        iEventRepository.delete(eventEntityOptional.get());
        return true;
    }

    @Override
    public List<EventEntity> getAllEvents() {
        return iEventRepository.findAll();
    }
}
