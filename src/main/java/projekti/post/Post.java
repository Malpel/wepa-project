package projekti.post;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.AbstractPersistable;
import projekti.account.Account;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Post extends AbstractPersistable<Long> {

    @NonNull
    private String content;

    @NonNull
    private int likes;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @CreationTimestamp
    private Timestamp timestamp;
}
