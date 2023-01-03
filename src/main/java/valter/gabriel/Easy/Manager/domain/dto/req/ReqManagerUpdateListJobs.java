package valter.gabriel.Easy.Manager.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import valter.gabriel.Easy.Manager.domain.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqManagerUpdateListJobs {
    private String name;
    private String description;
    private Boolean isFinished;
    private Boolean wantDelete;
    private Boolean isCanceled;
    private LocalDate finishDay;
}
