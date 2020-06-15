package projekti.post;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.account.Account;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAccountIn(List<Account> connectionsList);
}
