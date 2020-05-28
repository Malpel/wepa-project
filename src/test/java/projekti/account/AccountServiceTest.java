package projekti.account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

     @Before
     public void init() {
         accountRepository.save(
                 new Account("Iwas", "Hierbevor", "b4u", "already", "exists", null));
     }

    @Test
    public void newAccountIsSaved() {
        Account account  = accountService
                            .saveAccount("Maydup", "Nem", "m_nem", "asdqwe123", "mnem");

        assertTrue(accountRepository.findAll().contains(account));
    }

    @Test
    public void existingUsernameCannotBeUsed() {
        Account account = accountService
                .saveAccount("Newt", "Yousah", "b4u", "12345678", "Neyous");

        assertFalse(accountRepository.findAll().contains(account));
    }


    @Test
    public void accountIsFoundByUrlString() {
        Account account = accountRepository.save(
                new Account("Hugh", "Kerrs", "huker", "qwerty123", "hkerrs", null));

        Account foundByUrlString= accountService.getProfile(account.getUrlString());

        assertEquals(account, foundByUrlString);
    }

    @Test
    public void existingUrlStringCannotBeUsed() {
        Account account = accountService
                .saveAccount("Newt", "Yousah", "Neoyus", "12345678", "exists");

        assertFalse(accountRepository.findAll().contains(account));
    }

    @After
    public void tearDown() {
         accountRepository.deleteAll();
    }

}
