package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerEmployee;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdateListEmployers;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManager;
import valter.gabriel.Easy.Manager.exception.ApiRequestException;
import valter.gabriel.Easy.Manager.handle.ListHandle;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.LocalDate;
import java.util.List;

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
    public ResManager createNewEmployeeByManager(ReqManagerEmployee reqManagerEmployee) {
        Manager managerFounded = managerRepo
                .findById(reqManagerEmployee.getCnpj())
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário com CNPJ: " + reqManagerEmployee.getCnpj() + " não foi encontrado"));

        LocalDate localDateTime = LocalDate.now();

        reqManagerEmployee.getEmployees().forEach(employee -> {

            managerFounded.getEmployees().forEach(oldEmployee -> {
                if (oldEmployee.getCpf().equals(employee.getCpf())) {
                    throw new ApiRequestException(HttpStatus.CONFLICT, " -> Funcionário " + employee.getCpf() + " já está cadastrado para este patrão.");
                } else {
                    employee.setHireDate(localDateTime);
                }

            });
        });

        List<Employee> employeeList = new ListHandle<Employee>().updateList(managerFounded.getEmployees(), reqManagerEmployee.getEmployees());

        managerFounded.setEmployees(employeeList);

        managerRepo.save(managerFounded);

        ModelMapper mapper = new ModelMapper();
        return mapper.map(managerFounded, ResManager.class);
    }


    /**
     * Method used to find all employers of a given manager
     *
     * @param cnpj is the manager identifier
     * @return list of employers for this manager
     */
    public List<Employee> findAllEmployeeByManager(Long cnpj) {
        Manager manager = managerRepo.findById(cnpj)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário " + cnpj + " não foi encontrado!"));

        List<Employee> allEmployeeByManager = manager.getEmployees();
        if (allEmployeeByManager.isEmpty()) {
            throw new ApiRequestException(HttpStatus.OK, " -> Você ainda não cadastrou nenhum funcionário!");
        }
        return allEmployeeByManager;
    }

    /**
     * Method used to find employee by cpf
     * @param cpf
     * @return employee founded
     */
    public Employee findEmployeeById(Long cpf) {
        return employeeRepo
                .findById(cpf)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário " + cpf + " não foi encontrado!"));
    }

    /**
     * Method responsible for updating only the past employer of a given manager
     *
     * @param cnpj                          manager identifier
     * @param cpf                           employer identifier
     * @param reqManagerUpdateListEmployers object to update the employer
     * @return manager object with updated employer
     */
    public ResManager updateEmployerByManager(Long cnpj, Long cpf, ReqManagerUpdateListEmployers reqManagerUpdateListEmployers) {
        Manager manager = managerRepo.findById(cnpj)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário " + cnpj + " não foi encontrado!"));

        Employee employeeFounded = employeeRepo.findById(cpf).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário " + cpf + " não foi encontrado!"));

        Employee employee = manager.getEmployees()
                .stream()
                .filter(item -> item.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> O patrão " + cnpj + " não possui o funcionario " + cpf + " na sua lista de funcionários!"));

        employee.setIsActive(reqManagerUpdateListEmployers.getIsActive());
        employee.setBornDay(reqManagerUpdateListEmployers.getBornDay());
        employee.setEEmail(reqManagerUpdateListEmployers.getEEmail());
        employee.setEPhone(reqManagerUpdateListEmployers.getEPhone());
        employee.setEName(reqManagerUpdateListEmployers.getEName());
        employee.setPassword(reqManagerUpdateListEmployers.getPassword());
        employee.setJobs(employeeFounded.getJobs());

        managerRepo.save(manager);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(manager, ResManager.class);
    }

    public void deleteEmployeer(Long cnpj, Long cpf) {
        Manager manager = managerRepo.findById(cnpj)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> Usuário " + cnpj + " não foi encontrado!"));

        Employee employee = manager.getEmployees()
                .stream()
                .filter(item -> item.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> O patrão " + cnpj + " não possui o funcionario " + cpf + " na sua lista de funcionários!"));

        manager.getEmployees().remove(employee);
        managerRepo.save(manager);
        employeeRepo.delete(employee);


    }
}
