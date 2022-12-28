package valter.gabriel.Easy.Manager.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.OrderJob;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerEmployee;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerUpdate;
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
        Manager manager = managerService.updateManagerFieldsWithoutListEmployers(cnpj, reqManagerUpdate);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }
}
