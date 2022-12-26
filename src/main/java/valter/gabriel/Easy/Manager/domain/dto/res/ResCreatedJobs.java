package valter.gabriel.Easy.Manager.domain.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Manager;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResCreatedJobs {
    private ResManagerToJobCreated manager;
    private ResEmployeeToJobCreated employee;
}
