package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
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

        if (connection.getReceiver().getUsername().equals(getUser())) {
            connection.setAccepted(true);
            connection = connectionRepository.save(connection);

            accountService.addFriend(connection.getReceiver(), connection.getSender());
        }

        return connection;
    }

    public List<Connection> getRequests(Account account) {
        return connectionRepository.findBySenderOrReceiverAndIsAcceptedFalse(account);
    }

    @Transactional
    public void decline(Long id) {
        Connection connection = connectionRepository.getOne(id);

        if (connection.getReceiver().getUsername().equals(getUser())) {
            connectionRepository.delete(connection);
        }
    }

    public void disconnect(Account one, Account two) {
        if (getUser().equals(one.getUsername()) || getUser().equals(two.getUsername())) {
            accountService.removeFriend(one, two);
            connectionRepository.deleteBySenderAndReceiver(one, two);
        }
    }

    private String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
