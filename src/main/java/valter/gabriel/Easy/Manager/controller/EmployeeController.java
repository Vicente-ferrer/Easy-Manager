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
public class EmployeeController {

    private final EmployeeService employeeService;



    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;

    }

    @PatchMapping("employee/create-employee")
    public ResponseEntity<ResManager> createNewEmployer(@RequestBody ReqManagerEmployee reqManagerEmployee) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = employeeService.createNewEmployeeByManager(reqManagerEmployee);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.CREATED);
    }

    @GetMapping("employee/find-all-by-manager/{cnpj}")
    public ResponseEntity<List<Employee>> findAllEmployeeByManager(@PathVariable("cnpj") Long cnpj) {
        List<Employee> allEmployeeByManager = employeeService.findAllEmployeeByManager(cnpj);
        return new ResponseEntity<>(allEmployeeByManager, HttpStatus.OK);
    }

    @PutMapping("employee/update-from/{cnpj}/where-id/{cpf}")
    public ResponseEntity<ResManager> updateEmployer(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @RequestBody ReqManagerUpdateListEmployers reqManagerUpdateListEmployers) {
        ModelMapper mapper = new ModelMapper();
        Manager manager = employeeService.updateEmployerByManager(cnpj, cpf, reqManagerUpdateListEmployers);
        ResManager resManager = mapper.map(manager, ResManager.class);
        return new ResponseEntity<>(resManager, HttpStatus.OK);
    }

    @DeleteMapping("employee/delete/{cpf}/from/{cnpj}")
    public ResponseEntity<?> deleteManagerByCnpj(@PathVariable("cpf") Long cpf,@PathVariable("cnpj") Long cnpj){
        employeeService.deleteEmployeer(cnpj, cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
