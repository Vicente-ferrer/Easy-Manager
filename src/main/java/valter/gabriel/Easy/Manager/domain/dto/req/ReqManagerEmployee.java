package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Employee;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqManagerEmployee {
    private Long cnpj;
    private List<Employee> employees;
}
