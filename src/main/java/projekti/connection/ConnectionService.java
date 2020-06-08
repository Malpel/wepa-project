package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.account.Account;

import java.util.List;

@Service
public class ConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

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

    public Connection acceptConnection(Long connectionId) {
        Connection connection = connectionRepository.getOne(connectionId);
        connection.setAccepted(true);
        return connectionRepository.save(connection);
    }

    public List<Connection> getAcceptedConnections(Account account) {
        return connectionRepository.findAcceptedConnections(account);
    }

    public List<Connection> getNotAcceptedConnections(Account account) {
        return connectionRepository.findBySenderOrReceiverAndIsAcceptedFalse(account);
    }

    public void disconnect(Long id) {
        connectionRepository.deleteById(id);
    }
}
