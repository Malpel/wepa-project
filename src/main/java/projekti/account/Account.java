package projekti.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.connection.Connection;
import projekti.fileObject.FileObject;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    private String name;
    private String username;
    private String password;
    private String urlString;

    @OneToOne(mappedBy = "account")
    private FileObject fo;
}
