package valter.gabriel.Easy.Manager.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManager;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerCreated;
import valter.gabriel.Easy.Manager.service.EmployeeService;
import valter.gabriel.Easy.Manager.service.JobService;
import valter.gabriel.Easy.Manager.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/")
public class JobController {

    private final JobService jobService;


    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PatchMapping("manager/create-job")
    public ResponseEntity<ResCreatedJobs> createNewJob(@RequestBody OrderJob orderJob) {
        ResCreatedJobs resCreatedJobs = jobService.createNewJob(orderJob);
        return new ResponseEntity<>(resCreatedJobs, HttpStatus.CREATED);
    }

    @PutMapping("manager/update-job-/{id}/where-employer/{cpf}/manager/{cnpj}")
    public ResponseEntity<ResManager> updateJob(@PathVariable("id") Long id, @PathVariable("cpf") Long cpf, @PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdateListJobs reqManagerUpdateListJobs) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = jobService.updateJobsListByManager(cnpj, cpf,id, reqManagerUpdateListJobs);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }
}
