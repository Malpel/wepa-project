package projekti.post;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Post extends AbstractPersistable<Long> {

    @NonNull
    private String content;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> liked = new ArrayList<>();

    @CreationTimestamp
    private Timestamp timestamp;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> comments = new ArrayList<>();
}
