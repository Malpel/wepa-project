package projekti.account;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.fileObject.FileObject;
import projekti.skill.Skill;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @NonNull
    @Size(min = 2, max = 30)
    private String name;

    @NotEmpty
    @NonNull
    @Size(min = 1, max = 30)
    private String username;

    @NotEmpty
    @NonNull
    private String password;

    @NotEmpty
    @NonNull
    @Size(min = 1, max = 30)
    private String urlString;

    @OneToOne(mappedBy = "account")
    private FileObject fo;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Skill> skills = new ArrayList<>();
}
