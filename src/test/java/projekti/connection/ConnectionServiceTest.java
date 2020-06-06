package projekti.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.account.Account;
import projekti.account.AccountRepository;
import projekti.account.AccountService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionServiceTest {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    AccountService accountService;

    Account first;
    Account second;

    @Before
    public void init() {
        first = accountService.saveAccount("Iwas Hierbevor", "b4u", "already", "exists");
        second = accountService.saveAccount("Sec Ond", "tokand", "2wsx", "tokand");
    }

    @Test
    public void newConnectionIsSaved() {
        Connection connection = connectionService.newConnection(first, second);
        assertTrue(connectionRepository.findAll().contains(connection));
    }

    @Test
    public void allConnectionsAreReturned() {
        Connection connection = new Connection();
        connection.setSender(second);
        connection.setReceiver(first);
        connection.setAccepted(true);
        connectionRepository.save(connection);

        List<Connection> connections = connectionService.getConnections(first);

        assertEquals(2, connections.size());
    }

    @After
    public void tearDown() {
        connectionRepository.deleteAll();
    }

}
