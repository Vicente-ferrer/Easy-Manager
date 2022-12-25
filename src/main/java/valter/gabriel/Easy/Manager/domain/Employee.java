package valter.gabriel.Easy.Manager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

    @Id
    private Long cpf;
    private String eName;
    private String eEmail;
    private String ePhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;

    @OneToMany(targetEntity = Jobs.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employer_todo_fk", referencedColumnName = "cpf")
    private List<Jobs> jobs;


}
