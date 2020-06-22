package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.account.AccountService;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    AccountService accountService;


    public Connection requestConnection(Account sender, Account receiver) {
        Connection connection = new Connection();

        connection.setSender(sender);
        connection.setReceiver(receiver);
        connection.setAccepted(false);

        return connectionRepository.save(connection);
    }

    public Connection findById(Long id) {
        return connectionRepository.getOne(id);
    }

    @Transactional
    public Connection acceptConnection(Long connectionId) {
        Connection connection = connectionRepository.getOne(connectionId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (connection.getReceiver().getUsername().equals(auth.getName())) {
            connection.setAccepted(true);
            connection = connectionRepository.save(connection);

            accountService.addFriend(connection.getReceiver(), connection.getSender());
        }

        return connection;
    }

    public List<Connection> getRequests(Account account) {
        return connectionRepository.findBySenderOrReceiverAndIsAcceptedFalse(account);
    }

    public void decline(Long id) {
        connectionRepository.deleteById(id);
    }

    public Connection disconnect(Account one, Account two) {
        accountService.removeFriend(one, two);
        return connectionRepository.deleteBySenderAndReceiver(one, two);
    }
}
