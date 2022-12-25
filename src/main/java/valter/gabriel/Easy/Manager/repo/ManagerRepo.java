package valter.gabriel.Easy.Manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.Easy.Manager.domain.Manager;

public interface ManagerRepo extends JpaRepository<Manager, Long> {
}
