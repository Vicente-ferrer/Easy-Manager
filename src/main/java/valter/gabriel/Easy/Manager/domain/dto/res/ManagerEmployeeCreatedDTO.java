package valter.gabriel.Easy.Manager.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerEmployeeCreatedDTO {
    private Long cnpj;
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mPhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;
    private List<EmployeeWithoutJobsDTO> employees;
    private String password;
}
