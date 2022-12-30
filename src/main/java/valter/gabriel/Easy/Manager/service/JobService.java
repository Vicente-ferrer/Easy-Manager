package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.OrderJob;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdateListJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResEmployeeToJobCreated;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerToJobCreated;
import valter.gabriel.Easy.Manager.handle.UpdateList;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.JobRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final ManagerRepo managerRepo;
    private final EmployeeRepo employeeRepo;
    private final JobRepo jobRepo;

    public JobService(ManagerRepo managerRepo, EmployeeRepo employeeRepo, JobRepo jobRepo) {
        this.managerRepo = managerRepo;
        this.employeeRepo = employeeRepo;
        this.jobRepo = jobRepo;
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

    public void deleteJob(Long id){
        jobRepo.deleteById(id);

    }
}
