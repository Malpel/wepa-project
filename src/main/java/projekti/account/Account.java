package projekti.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.fileObject.FileObject;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String urlString;

    @OneToOne(mappedBy = "account")
    private FileObject fo;
}
