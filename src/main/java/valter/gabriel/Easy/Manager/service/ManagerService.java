package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerEmployee;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService {
    private final ManagerRepo managerRepo;

    @Autowired
    public ManagerService(ManagerRepo managerRepo) {
        this.managerRepo = managerRepo;
    }

    public Manager createNewManager(ReqManager reqManager) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = mapper.map(reqManager, Manager.class);
        managerRepo.save(manager);
        return manager;
    }

    public Manager createNewEmployeeByManager(ReqManagerEmployee reqEmployee) {
        ModelMapper mapper = new ModelMapper();
        Manager managerEmployee = mapper.map(reqEmployee, Manager.class);
        Manager managerFounded = managerRepo.findById(managerEmployee.getCnpj()).get();


        managerEmployee.setMPhone(managerFounded.getMPhone());
        managerEmployee.setGender(managerFounded.getGender());
        managerEmployee.setBornDay(managerFounded.getBornDay());
        managerEmployee.setMEmail(managerFounded.getMEmail());
        managerEmployee.setMCompany(managerFounded.getMCompany());
        managerEmployee.setIsActive(managerFounded.getIsActive());
        managerEmployee.setMName(managerFounded.getMName());

        managerEmployee.setEmployees(updateEmployeeList(managerFounded, reqEmployee));


        managerRepo.save(managerEmployee);
        return managerEmployee;
    }

    private List<Employee> updateEmployeeList(Manager manager, ReqManagerEmployee reqManagerEmployee) {
        /**
         * First we create a list the contains the current employers saved at database
         * Than we add all this itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using reqManagerEmployee.getEmployees()
         * Than we return the new list containg the previus and the current employers
         */
        List<Employee> listPreviusEmployer = manager.getEmployees();
        ArrayList<Employee> newEmployees = new ArrayList<>(listPreviusEmployer);
        newEmployees.addAll(reqManagerEmployee.getEmployees());
        return newEmployees;

    }

}
