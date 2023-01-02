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
@RequestMapping("/api/v1/")
public class ManagerController {
    private final ManagerService managerService;


    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("manager/sign-up")
    public ResponseEntity<ResManagerCreated> createNewManager(@RequestBody ReqManager reqManager) {
        ResManagerCreated newManager = managerService.createNewManager(reqManager);
        return new ResponseEntity<>(newManager, HttpStatus.CREATED);
    }

    @GetMapping("manager/find-by-cnpj/{cnpj}")
    public ResponseEntity<Manager> findManagerById(@PathVariable("cnpj") Long cnpj) {
        Manager manager = managerService.findManagerById(cnpj);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping("manager/update-fields-from/{cnpj}")
    public ResponseEntity<ResManager> updateManagerFieldsWithoutListEmployers(@PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdate reqManagerUpdate) {
        final ResManager resManager = managerService.updateManagerById(cnpj, reqManagerUpdate);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }

    @DeleteMapping("manager/delete/{cnpj}")
    public ResponseEntity<?> deleteManagerByCnpj(@PathVariable("cnpj") Long cnpj){
        managerService.deleteManagerById(cnpj);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
