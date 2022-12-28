package valter.gabriel.Easy.Manager.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Employee;
import valter.gabriel.Easy.Manager.domain.Gender;
import valter.gabriel.Easy.Manager.domain.Jobs;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResManager {
    private Long cnpj;
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mPhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;
    private List<Employee> employees;
    private String password;
}
