package valter.gabriel.Easy.Manager.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Manager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManager;
import valter.gabriel.Easy.Manager.domain.dto.req.ReqManagerEmployee;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManager;
import valter.gabriel.Easy.Manager.domain.dto.res.ResManagerCreated;
import valter.gabriel.Easy.Manager.service.ManagerService;

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

    @PatchMapping("manager/create-employee")
    public ResponseEntity<ResManager> createNewEmployer(@RequestBody ReqManagerEmployee reqManagerEmployee) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = managerService.createNewEmployeeByManager(reqManagerEmployee);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.CREATED);
    }
}
