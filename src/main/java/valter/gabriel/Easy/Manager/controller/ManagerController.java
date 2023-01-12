package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.ManagerEmployeeCreatedDTO;
import valter.gabriel.Easy.Manager.domain.dto.res.CreateManagerDTO;
import valter.gabriel.Easy.Manager.service.ManagerService;

@RestController
@RequestMapping("/api/v1/manager")
public class ManagerController {
    private final ManagerService managerService;


    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CreateManagerDTO> createNewManager(@RequestBody ReqManager reqManager) {
        CreateManagerDTO newManager = managerService.createNewManager(reqManager);
        return new ResponseEntity<>(newManager, HttpStatus.CREATED);
    }

    @GetMapping("/find-by-cnpj/{cnpj}")
    public ResponseEntity<Manager> findManagerById(@PathVariable("cnpj") Long cnpj) {
        Manager manager = managerService.findManagerById(cnpj);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping("/update-fields-from/{cnpj}")
    public ResponseEntity<ManagerEmployeeCreatedDTO> updateManagerFieldsWithoutListEmployers(@PathVariable("cnpj") Long cnpj, @RequestBody ReqManagerUpdate reqManagerUpdate) {
        final ManagerEmployeeCreatedDTO managerEmployeeCreatedDTO = managerService.updateManagerById(cnpj, reqManagerUpdate);
        return new ResponseEntity<>(managerEmployeeCreatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cnpj}")
    public ResponseEntity<?> deleteManagerByCnpj(@PathVariable("cnpj") Long cnpj){
        managerService.deleteManagerById(cnpj);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
