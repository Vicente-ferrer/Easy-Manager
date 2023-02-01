package valter.gabriel.Easy.Manager.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;

import java.util.List;
import java.util.Optional;

public interface ManagerRepo extends JpaRepository<Manager, Long> {
    @Query("SELECT m.employees FROM Manager m WHERE m.cnpj = :cnpj")
    List<Employee> findAllEmployeeByManager(Long cnpj);



}
