package scesi.org.check.event.model.response;


import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
    private Long id;
    private String title;
    private String description;
    private Instant nextControl;
    private Instant startTime;
    private Instant endTIme;
}
