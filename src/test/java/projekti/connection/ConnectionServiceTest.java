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
    public void newRequestIsSaved() {
        connectionService.requestConnection(first, second);
        assertEquals(1, connectionRepository.findAll().size());
    }

    @Test
    public void allConnectionsAreReturned() {
        Connection connection = new Connection();
        Account third = accountService.saveAccount("The Third", "kolmard", "password", "kolmard");

        connection.setSender(third);
        connection.setReceiver(first);
        connection.setAccepted(true);

        connectionRepository.save(connection);
        connectionService.requestConnection(first, second);

        List<Connection> connections = connectionService.getConnections(first);

        assertEquals(2, connections.size());
    }

    @After
    public void tearDown() {
        connectionRepository.deleteAll();
        accountService.deleteAll();
    }

}
