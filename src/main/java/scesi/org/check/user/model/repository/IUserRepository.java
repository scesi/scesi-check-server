package scesi.org.check.user.model.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import scesi.org.check.user.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
}
