package scesi.org.check.user.model.response;

import lombok.*;

import java.time.Instant;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolesOfUserResponse {
    private String rol;
    private Instant creationDate;
}
