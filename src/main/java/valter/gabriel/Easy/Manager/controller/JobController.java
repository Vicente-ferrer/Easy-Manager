package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Jobs;
import valter.gabriel.Easy.Manager.domain.dto.req.OrderJob;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdateListJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResCreatedJobs;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManager;
import valter.gabriel.Easy.Manager.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class JobController {

    private final JobService jobService;


    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }


    @PatchMapping("job/create")
    public ResponseEntity<ResCreatedJobs> createNewJob(@RequestBody OrderJob orderJob) {
        ResCreatedJobs resCreatedJobs = jobService.createNewJob(orderJob);
        return new ResponseEntity<>(resCreatedJobs, HttpStatus.CREATED);
    }

    @PutMapping("job/update/{id}/where-employer/{cpf}/from-manager/{cnpj}")
    public ResponseEntity<ResManager> updateJob(@PathVariable("id") Long id, @PathVariable("cpf") Long cpf, @PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdateListJobs reqManagerUpdateListJobs) {
        ResManager manager = jobService.updateJobsListByManager(cnpj, cpf,id, reqManagerUpdateListJobs);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @GetMapping("job/get/all")
    public ResponseEntity<List<Jobs>> getAllJobs(){
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    @GetMapping("job/get/all-canceled")
    public ResponseEntity<List<Jobs>> getAllJobsCanceled(){
        return new ResponseEntity<>(jobService.getAllJobsCanceled(), HttpStatus.OK);
    }

    @GetMapping("job/get/all-finished")
    public ResponseEntity<List<Jobs>> getAllJobsFinished(){
        return new ResponseEntity<>(jobService.getAllJobsFinished(), HttpStatus.OK);
    }

    @GetMapping("job/get/all-to-delete")
    public ResponseEntity<List<Jobs>> getAllJobsDelete(){
        return new ResponseEntity<>(jobService.getAllJobsDelete(), HttpStatus.OK);
    }

    @GetMapping("job/get/expires-today")
    public ResponseEntity<List<Jobs>> getJobsThatExpiresOnCurrentDay(){
        return new ResponseEntity<>(jobService.getJobsThatExpiresOnCurrentDay(), HttpStatus.OK);
    }

    @GetMapping("job/get-by-id/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable("id")Long id){
        return new ResponseEntity<>(jobService.getJobById(id), HttpStatus.OK);
    }

    @DeleteMapping("job/delete/{id}")
    public ResponseEntity<?> deleteManagerByCnpj(@PathVariable("id")Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
