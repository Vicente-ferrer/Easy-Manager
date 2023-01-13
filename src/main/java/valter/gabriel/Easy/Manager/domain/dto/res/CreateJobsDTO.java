package valter.gabriel.Easy.Manager.domain.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateJobsDTO {
    private ManagerToJobCreateDTO manager;
    private EmployeeToJobsCreatedDTO employee;
}
