package projekti.account;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.fileObject.FileObject;
import projekti.post.Post;
import projekti.skill.Skill;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NamedEntityGraph(name = "Account.foAndConnectionsAndSkills", attributeNodes = {
        @NamedAttributeNode("connections"),
        @NamedAttributeNode("fo")
})
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @NonNull
    @Size(min = 2, max = 30)
    private String name;

    @NotEmpty
    @NonNull
    @Size(min = 1, max = 30)
    @Column(unique = true)
    private String username;

    @NotEmpty
    @NonNull
    @Size(min = 8)
    private String password;

    @NotEmpty
    @NonNull
    @Size(min = 1, max = 30)
    @Column(unique = true)
    private String urlString;

    /*
    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private FileObject fo;
     */

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> connections = new ArrayList<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @OrderBy("compliments DESC")
    private List<Skill> skills = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}
