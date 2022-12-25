package valter.gabriel.Easy.Manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.Easy.Manager.domain.Jobs;

public interface JobsRepo extends JpaRepository<Jobs, Long> {
}
