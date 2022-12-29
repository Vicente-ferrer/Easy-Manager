package valter.gabriel.Easy.Manager.service;

import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerEmployee;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdateListEmployers;
import valter.gabriel.Easy.Manager.handle.UpdateList;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final ManagerRepo managerRepo;
    private final EmployeeRepo employeeRepo;

    public EmployeeService(ManagerRepo managerRepo, EmployeeRepo employeeRepo) {
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
    }

    /**
     * Method to create a new employer to specific manager
     *
     * @param reqManagerEmployee manager object that has to be passed
     * @return manager object with the list of employers updated
     */
    public Manager createNewEmployeeByManager(ReqManagerEmployee reqManagerEmployee) {
        Manager managerFounded = managerRepo.findById(reqManagerEmployee.getCnpj()).get();

        LocalDate localDateTime = LocalDate.now();
        reqManagerEmployee.getEmployees().forEach(employee -> {
            employee.setHireDate(localDateTime);
        });

        List<Employee> employeeList = new UpdateList<Employee>().updateList(managerFounded.getEmployees(), reqManagerEmployee.getEmployees());

        managerFounded.setEmployees(employeeList);

        managerRepo.save(managerFounded);
        return managerFounded;
    }


    /**
     * Method used to find all employers of a given manager
     * @param cnpj is the manager identifier
     * @return list of employers for this manager
     */
    public List<Employee> findAllEmployeeByManager(Long cnpj) {
        return managerRepo.findAllEmployeeByManager(cnpj);
    }

    /**
     * Method responsible for updating only the past employer of a given manager
     * @param cnpj manager identifier
     * @param cpf employer identifier
     * @param reqManagerUpdateListEmployers object to update the employer
     * @return manager object with updated employer
     */
    public Manager updateEmployerByManager(Long cnpj, Long cpf , ReqManagerUpdateListEmployers reqManagerUpdateListEmployers) {
        Optional<Manager> managerFounded = managerRepo.findById(cnpj);
        Employee employeeFounded = employeeRepo.findById(cpf).orElse(null);

        if (!managerFounded.isPresent()) {
            return null;
        }

        Manager myManager = managerFounded.get();

        Employee employee = myManager.getEmployees().stream().filter(item -> item.getCpf().equals(cpf)).findFirst().orElse(null);

        employee.setIsActive(reqManagerUpdateListEmployers.getIsActive());
        employee.setBornDay(reqManagerUpdateListEmployers.getBornDay());
        employee.setEEmail(reqManagerUpdateListEmployers.getEEmail());
        employee.setEPhone(reqManagerUpdateListEmployers.getEPhone());
        employee.setEName(reqManagerUpdateListEmployers.getEName());
        employee.setPassword(reqManagerUpdateListEmployers.getPassword());
        employee.setJobs(employeeFounded.getJobs());

        managerRepo.save(myManager);
        return myManager;
    }
}
