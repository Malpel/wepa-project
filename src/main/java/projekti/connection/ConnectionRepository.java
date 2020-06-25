package projekti.connection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    @Query(value = "SELECT * FROM CONNECTION WHERE (SENDER_ID = ?1 OR RECEIVER_ID = ?1) AND IS_ACCEPTED = FALSE", nativeQuery = true)
    List<Connection> findBySenderOrReceiverAndIsAcceptedFalse(Account account);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CONNECTION WHERE (SENDER_ID = ?1 AND RECEIVER_ID = ?2) OR (SENDER_ID = ?2 AND RECEIVER_ID = ?1)", nativeQuery = true)
    void deleteBySenderAndReceiver(Account one, Account two);

    @Query(value = "SELECT * FROM CONNECTION WHERE (SENDER_ID = ?1 AND RECEIVER_ID = ?2) OR (SENDER_ID = ?2 AND RECEIVER_ID = ?1)", nativeQuery = true)
    Connection findBySenderAndReceiver(Account sender, Account receiver);
}
