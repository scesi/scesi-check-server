package scesi.org.check.event.model.request;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record CreateEventRequest(
        @NotBlank(message = "title is required")
        @Size(max = 50, message = "Title cannot exceed 50 characters")
        String title,

        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        @NotBlank(message = "startTime is required")
        @FutureOrPresent(message = "starTime should be at present or future")
        Instant startTime,

        Instant endTime,
        Instant nextControl
) {
    public CreateEventRequest {
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("endTime should be large than startTime");
        }
        if (endTime != null && nextControl != null && !nextControl.isAfter(endTime)) {
            throw new IllegalArgumentException("nextControl should be large than endTime");
        }
    }
}
