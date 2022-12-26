package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Jobs;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqManagerJob {
    private Long cnpj;
    private List<Jobs> jobs;
}
