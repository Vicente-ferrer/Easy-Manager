package valter.gabriel.Easy.Manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import valter.gabriel.Easy.Manager.domain.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
