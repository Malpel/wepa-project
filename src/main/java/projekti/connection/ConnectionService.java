package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.account.Account;

@Service
public class ConnectionService {

    @Autowired
    ConnectionRepository connectionRepository;

    public Connection newConnection(Account sender, Account receiver) {
        Connection connection = new Connection();

        connection.setSender(sender);
        connection.setReceiver(receiver);
        connection.setStatus(Status.PENDING);

        return connectionRepository.save(connection);

    }

    public Connection findById(Long id) {
        return connectionRepository.getOne(id);
    }

    public Connection updateConnection(Long connectionId) {
        Connection connection = connectionRepository.getOne(connectionId);
        connection.setStatus(Status.ACCEPTED);
        return connectionRepository.save(connection);
    }
}
