package projekti.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.account.Account;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatedByInAndIsCommentFalse(List<Account> connectionsList, Pageable pageable);
}
