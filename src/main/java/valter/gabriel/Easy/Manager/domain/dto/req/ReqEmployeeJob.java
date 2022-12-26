package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;
import valter.gabriel.Easy.Manager.domain.Jobs;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqEmployeeJob {
    private Long cpf;
    private List<Jobs> jobs;
}
