package valter.gabriel.Easy.Manager.domain.dto.res;

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
public class EmployeeWithoutJobsDTO {
    private Long cpf;
    private String eName;
    private String eEmail;
    private String ePhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;

    private LocalDate hireDate;
    private String password;
}
