package scesi.org.check.user.model.projection;

import java.time.Instant;

public interface IRolesOfUserProjection {

    String getRol();

    Instant getCreationDate();
}