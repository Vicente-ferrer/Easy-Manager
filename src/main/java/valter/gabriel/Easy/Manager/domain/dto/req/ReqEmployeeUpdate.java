package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqEmployeeUpdate {
    private String eName;
    private String eEmail;
    private String ePhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;
    private LocalDate hireDate;
    private String password;
}
