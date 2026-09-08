package scesi.org.check.event.model.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record UpdateEventRequest(
        @Size(max = 50, message = "Title cannot exceed 50 characters")
        String title,

        @Size(max = 255, message = "Description cannot exceed 255 characters")
        String description,

        @FutureOrPresent(message = "starTime should be at present or future")
        Instant startTime,

        Instant endTime,
        Instant nextControl
) {
}
