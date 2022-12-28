package valter.gabriel.Easy.Manager.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResManagerUpdated {
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mPhone;
    private String password;
}
