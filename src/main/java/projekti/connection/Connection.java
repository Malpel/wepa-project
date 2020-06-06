package projekti.connection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Connection extends AbstractPersistable<Long> {

    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    private boolean isAccepted;
}
