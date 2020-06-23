package projekti.connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.account.AccountRepository;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;
    private Account two;
    private Connection connection;

    @Before
    public void init() {
        account = accountRepository.save(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));
        two = accountRepository.save(new Account("Hugh Kerrs", "huker", "asdqwe123", "huker"));
        connection = connectionRepository.save(new Connection(account, two, false));
    }

    @After
    public void tearDown() {
        connectionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser
    public void requestsWork() throws Exception {
        mockMvc.perform(
                post("/users/" + account.getId() + "/connect")
                .param("id", String.valueOf(account.getId())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        List<Connection> connections = connectionRepository.findAll();

        assertEquals(2, connections.size());
        assertFalse(connections.get(1).isAccepted());
    }

    @Test
    @WithMockUser("huker")
    @Transactional
    public void acceptingWorks() throws Exception {
        mockMvc.perform(
                post("/connection/" + connection.getId() + "/accept")
                .param("connectionId", String.valueOf(connection.getId())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertTrue(connection.isAccepted());
    }

    @Test
    @WithMockUser("huker")
    public void decliningWorks() throws Exception {
        mockMvc.perform(
                post("/connection/" + connection.getId() + "/decline")
                .param("connectionId", String.valueOf(connection.getId())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertTrue(connectionRepository.findAll().isEmpty());
    }

    @Test
    @WithMockUser("huker")
    public void disconnectingWorks() throws Exception {
        mockMvc.perform(
                post("/connection/disconnect")
                .param("oneId", String.valueOf(account.getId()))
                .param("twoId", String.valueOf(two.getId())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertTrue(connectionRepository.findAll().isEmpty());
    }
}
