package valter.gabriel.Easy.Manager.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Jobs;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeToJobsCreatedDTO {
    private Long cpf;
    private String eName;
    private List<Jobs> jobs;
}
