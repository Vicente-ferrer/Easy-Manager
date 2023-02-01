package valter.gabriel.Easy.Manager.domain.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import valter.gabriel.Easy.Manager.domain.Employee;

@Data
@AllArgsConstructor
public class LoginResponseEmployee {
    private String message;
    private Employee employee;
    private Long manager_id;
}
