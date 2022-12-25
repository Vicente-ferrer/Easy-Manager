package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqManager {
    private Long cnpj;
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mPhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;
}
