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

        List<Employee> employeeList = updateEmployeeListFromManager(managerFounded.getEmployees(), reqEmployee.getEmployees());
        managerEmployee.setEmployees(employeeList);

        managerRepo.save(managerEmployee);
        return managerEmployee;
    }

    public ResCreatedJobs createNewJob(OrderJob orderJob) {
        /**
         * Finding the manager to update job list
         */
        Optional<Manager> foundManager = managerRepo.findById(orderJob.getCnpj());

        if (foundManager.isPresent()) {
            /**
             * Creating a new manager object to update in the database
             */
            Manager managerCreateJob = new Manager();
            managerCreateJob.setMPhone(foundManager.get().getMPhone());
            managerCreateJob.setGender(foundManager.get().getGender());
            managerCreateJob.setBornDay(foundManager.get().getBornDay());
            managerCreateJob.setMEmail(foundManager.get().getMEmail());
            managerCreateJob.setMCompany(foundManager.get().getMCompany());
            managerCreateJob.setIsActive(foundManager.get().getIsActive());
            managerCreateJob.setMName(foundManager.get().getMName());
            managerCreateJob.setCnpj(foundManager.get().getCnpj());

            /**
             * finding in the employers list from the manager from specific employer passed as parameter on json
             */
            Optional<Employee> employee = foundManager.get().getEmployees().stream().filter(employer -> employer.getCpf().equals(orderJob.getCpf())).findAny();
            /**
             * set the jobs for specify employee
             */
            if (employee.isPresent()) {

                List<Jobs> listJobs = updateEmployeeJobsList(employee.get().getJobs(), orderJob.getJobs());
                employee.get().setJobs(listJobs);

                managerCreateJob.setEmployees(foundManager.get().getEmployees());

                managerRepo.save(managerCreateJob);

                ModelMapper mapper = new ModelMapper();
                ResCreatedJobs resCreatedJobs = new ResCreatedJobs();

                ResManagerToJobCreated managerToJobCreated = mapper.map(managerCreateJob, ResManagerToJobCreated.class);
                ResEmployeeToJobCreated resEmployeeToJobCreated = mapper.map(employee.get(), ResEmployeeToJobCreated.class);

                resCreatedJobs.setManager(managerToJobCreated);
                resCreatedJobs.setEmployee(resEmployeeToJobCreated);

                return resCreatedJobs;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    private List<Employee> updateEmployeeListFromManager(List<Employee> previusList, List<Employee> newList) {
        /**
         * We add all this itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using reqManagerEmployee.getEmployees()
         * Than we return the new list containg the previus and the current employers
         */
        ArrayList<Employee> newEmployees = new ArrayList<>(previusList);
        newEmployees.addAll(newList);
        return newEmployees;
    }


    private List<Jobs> updateEmployeeJobsList(List<Jobs> previusList, List<Jobs> newList) {
        /**
         * We add all this previus itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using reqManagerEmployee.getEmployees()
         * Than we return the new list containg the previus and the current employers
         */
        ArrayList<Jobs> newJobs = new ArrayList<>(previusList);
        newJobs.addAll(newList);
        return newJobs;
    }
}
