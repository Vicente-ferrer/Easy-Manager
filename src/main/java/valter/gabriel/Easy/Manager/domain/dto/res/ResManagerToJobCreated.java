package valter.gabriel.Easy.Manager.domain.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResManagerToJobCreated {
    private Long cnpj;
    private String mName;
    private String mCompany;
}
