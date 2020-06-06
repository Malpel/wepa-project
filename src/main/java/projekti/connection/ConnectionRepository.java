package projekti.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projekti.account.Account;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    @Query(value = "SELECT * FROM CONNECTION WHERE SENDER_ID = ?1 OR RECEIVER_ID = ?1 AND IS_ACCEPTED = TRUE", nativeQuery = true)
    List<Connection> findBySenderOrReceiverAndIsAccepted(Account account);
}
