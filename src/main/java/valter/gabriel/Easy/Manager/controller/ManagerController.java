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

    @DeleteMapping("manager/delete/{cnpj}")
    public ResponseEntity<String> deleteManagerByCnpj(@PathVariable("cnpj") Long cnpj){
        Manager manager = managerService.deleteManagerById(cnpj);
        String msg = "Usuário " + manager.getMName() + " deletado com sucesso: CNPJ -> " + manager.getCnpj();
        return new ResponseEntity<>(msg, HttpStatus.NO_CONTENT);
    }

}
