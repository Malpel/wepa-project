package projekti.account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account first;
    private Account second;

     @Before
     public void init() {
         Account account = new Account();

         String name = "Iwas Hierbevor";
         String username = "b4u";
         String password = "already_";
         String urlString = "exists";

         account.setName(name);
         account.setUsername(username);
         account.setPassword(password);
         account.setUrlString(urlString);

          first = accountRepository.save(account);
          second = accountRepository.save(new Account("Tester Man", "tester", "asdqwe123", "test"));
     }

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void newAccountIsSaved() {
        Account account  = accountService
                            .saveAccount(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));

        assertEquals(3, accountRepository.findAll().size());
        assertEquals("m_nem", accountRepository.findAll().get(2).getUsername());
    }

    @Test
    public void accountIsFoundByUrlString() {
        Account foundByUrlString = accountService.getAccountByUrlString("exists");
        assertEquals(first.getUsername(), foundByUrlString.getUsername());
    }

    @Test
    public void accountIsFoundByUsername() {
         Account foundByUsername = accountService.findByUsername("b4u");
         assertEquals(first.getUsername(), foundByUsername.getUsername());
    }

    @Test
    @Transactional
    public void accountIsFoundById() {
         Account foundById = accountService.findById(first.getId());
         assertEquals(first.getUsername(), foundById.getUsername());
    }

    @Test
    public void friendsAreAddedAndRemoved() {
         // addition
         accountService.addFriend(first, second);
         assertTrue(first.getConnections().contains(second));
         assertTrue(second.getConnections().contains(first));

         //removal
        accountService.removeFriend(first, second);
        assertFalse(first.getConnections().contains(second));
        assertFalse(second.getConnections().contains(first));
     }
}
