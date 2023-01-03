package valter.gabriel.Easy.Manager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Jobs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Boolean isFinished;
    private Boolean wantDelete;
    private Boolean isCanceled;
    private LocalDate creationDay;
    private LocalDate finishDay;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false)
    @JsonIgnore
    private Employee employee;

}
