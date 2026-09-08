package scesi.org.check.event.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scesi.org.check.event.model.entity.EventEntity;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
}
