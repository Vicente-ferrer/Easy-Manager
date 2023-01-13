package valter.gabriel.Easy.Manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.dto.req.*;
import valter.gabriel.Easy.Manager.domain.dto.res.LoginResponse;
import valter.gabriel.Easy.Manager.domain.dto.res.ManagerEmployeeCreatedDTO;
import valter.gabriel.Easy.Manager.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;



    @Autowired
    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginManager(@RequestBody LoginForm loginForm) {
        LoginResponse loginResponse = employeeService.employeeLogin(loginForm);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(@RequestBody LoginForm loginForm) {
        String str = employeeService.setNewPassword(loginForm);
        return new ResponseEntity<>(str, HttpStatus.OK);
    }


    @PatchMapping("/create-employee")
    public ResponseEntity<ManagerEmployeeCreatedDTO> createNewEmployer(@RequestBody ReqManagerEmployee reqManagerEmployee) {
        ManagerEmployeeCreatedDTO newEmployeeByManager = employeeService.createNewEmployeeByManager(reqManagerEmployee);
        return new ResponseEntity<>(newEmployeeByManager, HttpStatus.CREATED);
    }

    @GetMapping("/find-all-by-manager/{cnpj}")
    public ResponseEntity<List<Employee>> findAllEmployeeByManager(@PathVariable("cnpj") Long cnpj) {
        List<Employee> allEmployeeByManager = employeeService.findAllEmployeeByManager(cnpj);
        return new ResponseEntity<>(allEmployeeByManager, HttpStatus.OK);
    }

    @GetMapping("/find-by-id/{cpf}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("cpf") Long cpf) {
        Employee employee = employeeService.findEmployeeById(cpf);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("/update-from/{cnpj}/where-id/{cpf}")
    public ResponseEntity<ManagerEmployeeCreatedDTO> updateEmployer(@PathVariable("cnpj") Long cnpj, @PathVariable("cpf") Long cpf, @RequestBody ReqManagerUpdateListEmployers reqManagerUpdateListEmployers) {
        ManagerEmployeeCreatedDTO managerEmployeeCreatedDTO = employeeService.updateEmployerByManager(cnpj, cpf, reqManagerUpdateListEmployers);
        return new ResponseEntity<>(managerEmployeeCreatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cpf}/from/{cnpj}")
    public ResponseEntity<?> deleteManagerByCnpj(@PathVariable("cpf") Long cpf,@PathVariable("cnpj") Long cnpj){
        employeeService.deleteEmployeer(cnpj, cpf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
