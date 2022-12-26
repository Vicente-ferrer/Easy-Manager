package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import valter.gabriel.Easy.Manager.domain.Jobs;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderJob {
    private Long cnpj;
    private Long cpf;
    private List<Jobs> jobs;
}
