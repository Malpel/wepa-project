package projekti.post;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Post extends AbstractPersistable<Long> {

    @NonNull
    @NotEmpty
    @Size(min = 1, max = 10000)
    private String content;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account createdBy;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Account> liked = new ArrayList<>();

    @CreationTimestamp
    private Timestamp timestamp;

    @OneToMany(fetch = FetchType.LAZY)
    @OrderBy("timestamp DESC")
    private List<Post> comments = new ArrayList<>();

    @NonNull
    private boolean isComment;
}
