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
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.JobsRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {
    private final ManagerRepo managerRepo;
    private final EmployeeRepo employeeRepo;
    private final JobsRepo jobsRepo;

    @Autowired
    public ManagerService(ManagerRepo managerRepo, EmployeeRepo employeeRepo, JobsRepo jobsRepo) {
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
        this.jobsRepo = jobsRepo;
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

        managerEmployee.setEmployees(updateEmployeeListFromManager(managerFounded, reqEmployee.getEmployees()));

        managerRepo.save(managerEmployee);
        return managerEmployee;
    }

    public ResCreatedJobs createNewJob(JobsReq jobsReq) {


        /**
         * Finding the manager to update job list
         */
        Manager managerJob = new Manager();
        Manager foundManager = managerRepo.findById(jobsReq.getCnpj()).get();

        /**
         * Creating a new manager object to update in the database
         */
        managerJob.setMPhone(foundManager.getMPhone());
        managerJob.setGender(foundManager.getGender());
        managerJob.setBornDay(foundManager.getBornDay());
        managerJob.setMEmail(foundManager.getMEmail());
        managerJob.setMCompany(foundManager.getMCompany());
        managerJob.setIsActive(foundManager.getIsActive());
        managerJob.setMName(foundManager.getMName());
        managerJob.setCnpj(foundManager.getCnpj());


        /**
         * finding in the employers list from the manager the specify employer passed as parameter on json
         */
        Optional<Employee> employee = foundManager.getEmployees().stream().filter(item -> item.getCpf().equals(jobsReq.getCpf())).findAny();
        /**
         * set the jobs for specify employee
         */
        if (employee.isPresent()){
            employee.get().setJobs(updateEmployeeJobsList(employee.get(), jobsReq.getJobs()));
            managerJob.setEmployees(updateEmployeeListFromManager(foundManager, foundManager.getEmployees()));
            managerRepo.save(managerJob);

            ModelMapper mapper = new ModelMapper();
            ResCreatedJobs resCreatedJobs = new ResCreatedJobs();

            ResManagerToJobCreated managerToJobCreated = mapper.map(managerJob, ResManagerToJobCreated.class);
            ResEmployeeToJobCreated resEmployeeToJobCreated = mapper.map(employee.get(), ResEmployeeToJobCreated.class);

            resCreatedJobs.setManager(managerToJobCreated);
            resCreatedJobs.setEmployee(resEmployeeToJobCreated);

            return resCreatedJobs;
        }else{
            return null;
        }



    }


    private List<Employee> updateEmployeeListFromManager(Manager manager, List<Employee> listE) {
        /**
         * First we create a list the contains the current employers saved at database
         * Than we add all this itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using reqManagerEmployee.getEmployees()
         * Than we return the new list containg the previus and the current employers
         */
        List<Employee> listPreviusEmployer = manager.getEmployees();
        ArrayList<Employee> newEmployees = new ArrayList<>(listPreviusEmployer);
        newEmployees.addAll(listE);
        return newEmployees;
    }


    private List<Jobs> updateEmployeeJobsList(Employee employee, List<Jobs> jobsList) {
        /**
         * First we create a list the contains the current employers saved at database
         * Than we add all this itens to our newEmployees Array List
         * Finally we also add all itens that as passed as parameter using reqManagerEmployee.getEmployees()
         * Than we return the new list containg the previus and the current employers
         */
        List<Jobs> listPreviusJobs = employee.getJobs();
        ArrayList<Jobs> newJobs = new ArrayList<>(listPreviusJobs);
        newJobs.addAll(jobsList);
        return newJobs;
    }
}
