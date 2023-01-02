package valter.gabriel.Easy.Manager.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DefaultErro {
    private String code;
    private String message;
}
