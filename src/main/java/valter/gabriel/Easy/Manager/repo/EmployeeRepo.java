package valter.gabriel.Easy.Manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
