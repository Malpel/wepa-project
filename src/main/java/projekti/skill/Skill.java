package projekti.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Skill extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 1)
    private String name;

    private int compliments;

    @ManyToOne
    private Account account;
}
