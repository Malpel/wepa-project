package projekti.fileObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileObject extends AbstractPersistable<Long> {

    @Column(length = 16000000)
    private byte[] content;

    @OneToOne
    private Account account;
}
