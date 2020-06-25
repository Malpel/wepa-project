package projekti.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    @EntityGraph(value = "Account.foAndConnectionsAndSkills")
    Account findByUrlString(String urlString);

    List<Account> findByNameContainingIgnoreCase(String name);
}
