package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.LoginResponse;
import valter.gabriel.Easy.Manager.domain.dto.res.ManagerEmployeeCreatedDTO;
import valter.gabriel.Easy.Manager.domain.dto.res.CreateManagerDTO;
import valter.gabriel.Easy.Manager.service.ManagerService;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginManager(@RequestBody LoginForm loginForm) {
        LoginResponse loginResponse = managerService.managerLogin(loginForm);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody LoginForm loginForm) {
        String str = managerService.setNewPassword(loginForm);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }

    @GetMapping("/find-by-cnpj/{cnpj}")
    public ResponseEntity<Manager> findManagerById(@PathVariable("cnpj") Long cnpj) {
        Manager manager = managerService.findManagerById(cnpj);
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Manager>> findAll() {
        return new ResponseEntity<>(managerService.findAll(), HttpStatus.OK);
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
