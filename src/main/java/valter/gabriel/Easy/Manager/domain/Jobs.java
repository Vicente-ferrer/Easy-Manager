package valter.gabriel.Easy.Manager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
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
    private String urlImage;
    private LocalDateTime creationDay;

}
