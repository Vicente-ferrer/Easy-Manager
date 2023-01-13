package valter.gabriel.Easy.Manager.domain.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long cnpj;
    private String message;
}
