package scesi.org.check.latefee.model.event;

import scesi.org.check.latefee.model.projection.ILateFeeNotificationProjection;

import java.util.List;

public record LateFeeGeneratedEvent(List<ILateFeeNotificationProjection> notif) {
}
