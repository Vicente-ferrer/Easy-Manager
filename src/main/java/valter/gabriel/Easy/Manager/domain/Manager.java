package valter.gabriel.Easy.Manager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "manager_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Manager implements Serializable {

    @Id
    private Long cnpj;
    private String mName;
    private String mEmail;
    private String mCompany;
    private String mPhone;
    private Integer isActive;
    private Gender gender;
    private LocalDate bornDay;

    private LocalDate creationDate;
    private String password;

    @OneToMany(targetEntity = Employee.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_fk", referencedColumnName = "cnpj")
    private List<Employee> employees;
}
