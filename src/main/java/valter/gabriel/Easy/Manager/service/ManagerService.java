package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResEmployeeToJobCreated;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerToJobCreated;
import valter.gabriel.Easy.Manager.handle.UpdateList;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Method to create a new employer to specific manager
     * @param reqManagerEmployee manager object that has to be passed
     * @return manager object with the list of employers updated
     */
    public Manager createNewEmployeeByManager(ReqManagerEmployee reqManagerEmployee) {
        ModelMapper mapper = new ModelMapper();
        Manager managerEmployee = mapper.map(reqManagerEmployee, Manager.class);
        Manager managerFounded = managerRepo.findById(managerEmployee.getCnpj()).get();

        managerEmployee.setMPhone(managerFounded.getMPhone());
        managerEmployee.setGender(managerFounded.getGender());
        managerEmployee.setBornDay(managerFounded.getBornDay());
        managerEmployee.setMEmail(managerFounded.getMEmail());
        managerEmployee.setMCompany(managerFounded.getMCompany());
        managerEmployee.setIsActive(managerFounded.getIsActive());
        managerEmployee.setMName(managerFounded.getMName());


        List<Employee> employeeList = new UpdateList<Employee>().updateList(managerFounded.getEmployees(), reqManagerEmployee.getEmployees());
        managerEmployee.setEmployees(employeeList);

        managerRepo.save(managerEmployee);
        return managerEmployee;
    }

    /**
     * Method to create a job for specific manager and employer
     * @param orderJob object that receive cnpj of manager and cpf of employer that has to do the work
     * @return object resCreatedJobs that represents the manager of job and the employer that jobs has associated
     */
    public ResCreatedJobs createNewJob(OrderJob orderJob) {
        /**
         * Finding the manager to update job list
         */
        Optional<Manager> foundManager = managerRepo.findById(orderJob.getCnpj());

        if (!foundManager.isPresent()) {
         return null;
        }

        /**
         * finding in the employers list from the manager specific employer passed as parameter on json
         */
        Optional<Employee> employee = foundManager.get().getEmployees().stream().filter(employer -> employer.getCpf().equals(orderJob.getCpf())).findAny();
        /**
         * set the jobs for specify employee
         */
        if (!employee.isPresent()) {
            return null;
        }


        List<Jobs> listJobs = new UpdateList<Jobs>().updateList(employee.get().getJobs(), orderJob.getJobs());
        employee.get().setJobs(listJobs);

        managerRepo.save(foundManager.get());

        ModelMapper mapper = new ModelMapper();
        ResCreatedJobs resCreatedJobs = new ResCreatedJobs();

        ResManagerToJobCreated managerToJobCreated = mapper.map(foundManager.get(), ResManagerToJobCreated.class);
        ResEmployeeToJobCreated resEmployeeToJobCreated = mapper.map(employee.get(), ResEmployeeToJobCreated.class);

        resCreatedJobs.setManager(managerToJobCreated);
        resCreatedJobs.setEmployee(resEmployeeToJobCreated);

        return resCreatedJobs;
    }
}
