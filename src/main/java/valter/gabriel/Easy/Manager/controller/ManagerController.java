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
import valter.gabriel.Easy.Manager.service.ManagerService;

import java.util.List;

@RestController
@RequestMapping("/")
public class ManagerController {
    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("manager/sign-up")
    public ResponseEntity<ResManagerCreated> createNewManager(@RequestBody ReqManager reqManager) {
        managerService.createNewManager(reqManager);
        ModelMapper mapper = new ModelMapper();
        ResManagerCreated resManager = mapper.map(reqManager, ResManagerCreated.class);
        return new ResponseEntity<>(resManager, HttpStatus.CREATED);
    }

    @PatchMapping("manager/create-job")
    public ResponseEntity<ResCreatedJobs> createNewJob(@RequestBody OrderJob orderJob) {
        ResCreatedJobs resCreatedJobs = managerService.createNewJob(orderJob);
        return new ResponseEntity<>(resCreatedJobs, HttpStatus.CREATED);
    }

    @PatchMapping("manager/create-employee")
    public ResponseEntity<ResManager> createNewEmployer(@RequestBody ReqManagerEmployee reqManagerEmployee) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = managerService.createNewEmployeeByManager(reqManagerEmployee);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.CREATED);
    }

    @GetMapping("/find-all-employers-by-manager/{cnpj}")
    public ResponseEntity<List<Employee>> findAllEmployeeByManager(@PathVariable("cnpj") Long cnpj) {
        List<Employee> allEmployeeByManager = managerService.findAllEmployeeByManager(cnpj);
        return new ResponseEntity<>(allEmployeeByManager, HttpStatus.OK);
    }

    @GetMapping("/find-manager-by-cnpj/{cnpj}")
    public ResponseEntity<Manager> findManagerById(@PathVariable("cnpj") Long cnpj) {
        Manager manager = managerService.findManagerById(cnpj);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping("manager/update-field-from/{cnpj}")
    public ResponseEntity<ResManager> updateManagerFieldsWithoutListEmployers(@PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdate reqManagerUpdate) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = managerService.updateManagerById(cnpj, reqManagerUpdate);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }

    @PutMapping("manager/update-employer-from/{cnpj}/where-id/{cpf}")
    public ResponseEntity<ResManager> updateEmployer(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @RequestBody ReqManagerUpdateListEmployers reqManagerUpdateListEmployers) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = managerService.updateEmployerByManager(cnpj, cpf, reqManagerUpdateListEmployers);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }

    @PutMapping("manager/update-job-/{id}/where-employer/{cpf}/manager/{cnpj}")
    public ResponseEntity<ResManager> updateEmployer(@PathVariable("id") Long id, @PathVariable("cpf") Long cpf, @PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdateListJobs reqManagerUpdateListJobs) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = managerService.updateJobsListByManager(cnpj, cpf,id, reqManagerUpdateListJobs);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }
}
