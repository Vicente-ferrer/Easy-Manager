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
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {
    private final ManagerRepo managerRepo;
    private final EmployeeRepo employeeRepo;

    @Autowired
    public ManagerService(ManagerRepo managerRepo, EmployeeRepo employeeRepo) {
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
    }

    /**
     * Method created to create a new manager
     * Accessed only from the admin panel
     * @param reqManager, object with the necessary attributes for creating the manager, the list of
     * jobs and employers because this is something done after the creation of the manager
     */
    public void createNewManager(ReqManager reqManager) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = mapper.map(reqManager, Manager.class);

        LocalDate localDateTime = LocalDate.now();
        manager.setCreationDate(localDateTime);

        managerRepo.save(manager);
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
     * Method to create a job for specific manager and employer
     *
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

        LocalDateTime localDateTime = LocalDateTime.now();
        orderJob.getJobs().forEach(job -> {
            job.setCreationDay(localDateTime);
        });

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

    /**
     * Method used to find all employers of a given manager
     * @param cnpj is the manager identifier
     * @return list of employers for this manager
     */
    public List<Employee> findAllEmployeeByManager(Long cnpj) {
        return managerRepo.findAllEmployeeByManager(cnpj);
    }

    /**
     * Method used to update a manager by its identifier
     * @param cnpj manager identifier
     * @param reqManagerUpdate manager object to be updated
     * @return manager updated
     */
    public Manager updateManagerById(Long cnpj, ReqManagerUpdate reqManagerUpdate) {
        Optional<Manager> managerFounded = managerRepo.findById(cnpj);

        if (!managerFounded.isPresent()) {
            return null;
        }

        Manager myManager = managerFounded.get();

        myManager.setMName(reqManagerUpdate.getMName());
        myManager.setMEmail(reqManagerUpdate.getMEmail());
        myManager.setMPhone(reqManagerUpdate.getMPhone());
        myManager.setPassword(reqManagerUpdate.getPassword());
        myManager.setMCompany(reqManagerUpdate.getMCompany());

        managerRepo.save(myManager);
        return myManager;
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

    /**
     * Method to update a specific job of a certain employee
     * @param cnpj manager identifier
     * @param cpf employer identifier
     * @param id job id
     * @param reqManagerUpdateListJobs object to update the job
     * @return Manager with updated fields
     */
    public Manager updateJobsListByManager(Long cnpj, Long cpf, Long id, ReqManagerUpdateListJobs reqManagerUpdateListJobs) {
        Optional<Manager> managerFounded = managerRepo.findById(cnpj);
        Employee employeeFounded = employeeRepo.findById(cpf).orElse(null);

        if (!managerFounded.isPresent()) {
            return null;
        }

        Manager myManager = managerFounded.get();

        Employee employee = myManager.getEmployees().stream().filter(item -> item.getCpf().equals(cpf)).findFirst().orElse(null);
        Jobs job = employee.getJobs().stream().filter(item -> item.getId().equals(id)).findFirst().orElse(null);

        job.setDescription(reqManagerUpdateListJobs.getDescription());
        job.setName(reqManagerUpdateListJobs.getName());
        job.setUrlImage(reqManagerUpdateListJobs.getUrlImage());

        LocalDateTime localDateTime = LocalDateTime.now();
        job.setCreationDay(localDateTime);

        employee.setIsActive(employeeFounded.getIsActive());
        employee.setBornDay(employeeFounded.getBornDay());
        employee.setEEmail(employeeFounded.getEEmail());
        employee.setEPhone(employeeFounded.getEPhone());
        employee.setEName(employeeFounded.getEName());
        employee.setPassword(employeeFounded.getPassword());

        managerRepo.save(myManager);
        return myManager;
    }

    /**
     * Method used to find a manager by his identifier
     * @param cnpj identifier manager
     * @return manager found
     */
    public Manager findManagerById(Long cnpj) {
        Optional<Manager> manager = managerRepo.findById(cnpj);
        return manager.orElse(null);
    }
}
