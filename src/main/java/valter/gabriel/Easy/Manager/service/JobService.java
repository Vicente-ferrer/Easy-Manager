package valter.gabriel.Easy.Manager.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.OrderJob;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdateListJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResEmployeeToJobCreated;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManager;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerToJobCreated;
import valter.gabriel.Easy.Manager.exception.ApiRequestException;
import valter.gabriel.Easy.Manager.handle.ListHandle;
import valter.gabriel.Easy.Manager.repo.EmployeeRepo;
import valter.gabriel.Easy.Manager.repo.JobRepo;
import valter.gabriel.Easy.Manager.repo.ManagerRepo;

import java.time.LocalDateTime;
import java.util.List;

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
        Manager manager = managerRepo.findById(orderJob.getCnpj()).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Patrão " + orderJob.getCnpj() + " não encontrado!"));

        LocalDateTime localDateTime = LocalDateTime.now();
        orderJob.getJobs().forEach(job -> {
            job.setCreationDay(localDateTime);
            job.setIsFinished(false);
            job.setIsCanceled(false);
            job.setWantDelete(false);
        });

        /**
         * finding in the employers list from the manager specific employer passed as parameter on json
         */
        Employee employee = manager.getEmployees()
                .stream()
                .filter(employer -> employer.getCpf().equals(orderJob.getCpf()))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Funcionário " + orderJob.getCpf() + " não encontrado!"));
        /**
         * set the jobs for specify employee
         */
        List<Jobs> listJobs = new ListHandle<Jobs>().updateList(employee.getJobs(), orderJob.getJobs());
        employee.setJobs(listJobs);

        managerRepo.save(manager);

        ModelMapper mapper = new ModelMapper();
        ResCreatedJobs resCreatedJobs = new ResCreatedJobs();

        ResManagerToJobCreated managerToJobCreated = mapper.map(manager, ResManagerToJobCreated.class);
        ResEmployeeToJobCreated resEmployeeToJobCreated = mapper.map(employee, ResEmployeeToJobCreated.class);

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
    public ResManager updateJobsListByManager(Long cnpj, Long cpf, Long id, ReqManagerUpdateListJobs reqManagerUpdateListJobs) {
        Manager manager = managerRepo.findById(cnpj)
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Patrão " + cnpj + " não encontrado!"));

        Employee employeeFounded = employeeRepo.findById(cpf).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Patrão " + cpf + " não encontrado!"));

        Employee employee = manager.getEmployees()
                .stream()
                .filter(item -> item.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> O patrão " + cnpj + " não possui o funcionario " + cpf + " na sua lista de funcionários!"));


        Jobs job = employee.getJobs()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, " -> O funcionário " + cpf + " não possui o trabalho " + id + " atribuído!"));

        job.setDescription(reqManagerUpdateListJobs.getDescription());
        job.setName(reqManagerUpdateListJobs.getName());
        job.setIsCanceled(reqManagerUpdateListJobs.getIsCanceled());
        job.setFinishDay(reqManagerUpdateListJobs.getFinishDay());
        job.setWantDelete(reqManagerUpdateListJobs.getWantDelete());
        job.setIsFinished(reqManagerUpdateListJobs.getIsFinished());

        LocalDateTime localDateTime = LocalDateTime.now();
        job.setCreationDay(localDateTime);

        employee.setIsActive(employeeFounded.getIsActive());
        employee.setBornDay(employeeFounded.getBornDay());
        employee.setEEmail(employeeFounded.getEEmail());
        employee.setEPhone(employeeFounded.getEPhone());
        employee.setEName(employeeFounded.getEName());
        employee.setPassword(employeeFounded.getPassword());

        managerRepo.save(manager);

        ModelMapper mapper = new ModelMapper();

        return mapper.map(manager, ResManager.class);
    }

    public void deleteJob(Long id){
        Jobs job = jobRepo.findById(id).orElseThrow(() -> new ApiRequestException(HttpStatus.NOT_FOUND, "Trabalho de id " + id + " não encontrado!"));
        jobRepo.delete(job);

    }
}
