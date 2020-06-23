package projekti.connection;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.account.AccountService;


import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionServiceTest {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private AccountService accountService;

    private Account first;
    private Account second;
    private Connection connection;

    @Before
    public void init() {
        first = accountService.saveAccount(new Account("Iwas Hierbevor", "b4u", "already", "exists"));
        second = accountService.saveAccount(new Account("Sec Ond", "tokand", "2wsx", "tokand"));
        connection = connectionRepository.save(new Connection(first, second, false));
    }

    @After
    public void tearDown() {
        connectionRepository.deleteAll();
        accountService.deleteAll();
    }

    @Test
    public void newRequestIsSaved() {
        Connection connection = connectionService.requestConnection(first, second);
        Connection c = connectionRepository.findAll().get(0);
        assertEquals(connection.getSender().getUsername(), c.getSender().getUsername());
        assertEquals(connection.getReceiver().getUsername(), c.getReceiver().getUsername());
        assertFalse(c.isAccepted());
    }

    @Test
    @Transactional
    public void connectionIsFoundById() {
        assertEquals(connection, connectionService.findById(connection.getId()));
    }

    @Test
    @WithMockUser(username = "tokand")
    public void receiverCanAcceptConnection() {
        connection = connectionService.acceptConnection(connection.getId());
        assertTrue(connection.isAccepted());
    }

    @Test
    @WithMockUser(username = "b4u")
    public void senderCannotAcceptConnection() {
        connection = connectionService.acceptConnection(connection.getId());
        assertFalse(connection.isAccepted());
    }

    @Test
    @WithMockUser(username = "tokand")
    public void connectionIsDeclinedAndDeleted() {
        connectionService.decline(connection.getId());
        assertTrue(connectionRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "b4u")
    public void senderCannotDeclineConnection() {
        connectionService.decline(connection.getId());
        assertFalse(connectionRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser(username = "tokand")
    public void disconnectRemovesConnection() {
        connectionService.disconnect(second, first);
        assertTrue(connectionRepository.findAll().isEmpty());
    }

}
