package scesi.org.check.event.controller;

import jakarta.websocket.server.PathParam;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scesi.org.check.core.model.response.StandardResponse;
import scesi.org.check.event.model.entity.EventEntity;
import scesi.org.check.event.model.request.CreateEventRequest;
import scesi.org.check.event.model.request.UpdateEventRequest;
import scesi.org.check.event.model.response.EventResponse;
import scesi.org.check.event.service.IEventService;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final IEventService iEventService;

    public EventController(IEventService iEventService) {
        this.iEventService = iEventService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<StandardResponse<EventResponse>> getEventById(
            @PathVariable("eventId") final Long eventId
    ) {
        final EventEntity eventEntity = iEventService.getById(eventId);
        final EventResponse eventResponse = generateEventResponse(eventEntity);
        final StandardResponse<EventResponse> standardResponse = StandardResponse.<EventResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Event retrieved successfully")
                .data(eventResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @GetMapping("/")
    public ResponseEntity<StandardResponse<List<EventResponse>>> getAllEvents() {
        final List<EventEntity> eventEntityList = iEventService.getAllEvents();
        final List<EventResponse> eventResponseList = eventEntityList.stream()
                .map(this::generateEventResponse)
                .toList();
        final StandardResponse<List<EventResponse>> standardResponse = StandardResponse.<List<EventResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Events retrieved successfully")
                .data(eventResponseList)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<StandardResponse<Boolean>> deleteEventById(
            @PathVariable("eventId") final Long eventId
    ) {
        final Boolean eventDeleted = iEventService.deleteEvent(eventId);
        final StandardResponse<Boolean> standardResponse = StandardResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Event deleted successfully")
                .data(eventDeleted)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<StandardResponse<EventResponse>> updateEvent(
            @PathVariable("eventId") final Long eventId,
            @Validated
            @RequestBody final UpdateEventRequest request
    ) {
        final EventEntity eventEntity = iEventService.updateEvent(request, eventId);
        final EventResponse eventResponse = generateEventResponse(eventEntity);
        final StandardResponse<EventResponse> standardResponse = StandardResponse.<EventResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Event updated successfully")
                .data(eventResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(standardResponse);
    }

    @PostMapping("/")
    public ResponseEntity<StandardResponse<EventResponse>> createEvent(
            @Validated
            @RequestBody final CreateEventRequest request
    ) {
        final EventEntity eventEntity = iEventService.createEvent(request);
        final EventResponse eventResponse = generateEventResponse(eventEntity);
        final StandardResponse<EventResponse> standardResponse = StandardResponse.<EventResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Event created successfully")
                .data(eventResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(standardResponse);
    }

    private EventResponse generateEventResponse(EventEntity event) {
        return EventResponse.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .nextControl(event.getNextControl())
                .startTime(event.getStartTime())
                .endTIme(event.getEndTime())
                .build();
    }
}
