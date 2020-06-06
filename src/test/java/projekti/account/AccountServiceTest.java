package projekti.account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    Account first;

     @Before
     public void init() {
         Account account = new Account();

         String name = "Iwas Hierbevor";
         String username = "b4u";
         String password = "already";
         String urlString = "exists";

         account.setName(name);
         account.setUsername(username);
         account.setPassword(password);
         account.setUrlString(urlString);

          first = accountRepository.save(account);
     }

    @Test
    public void newAccountIsSaved() {
        Account account  = accountService
                            .saveAccount("Maydup Nem", "m_nem", "asdqwe123", "mnem");

        assertTrue(accountRepository.findAll().contains(account));
    }

    @Test
    public void existingUsernameCannotBeUsed() {
        Account account = accountService
                .saveAccount("Newt Yousah", "b4u", "12345678", "Neyous");

        assertFalse(accountRepository.findAll().contains(account));
    }


    @Test
    public void accountIsFoundByUrlString() {
        Account foundByUrlString = accountService.getAccountByUrl(first.getUrlString());
        assertEquals(first, foundByUrlString);
    }

    @Test
    public void existingUrlStringCannotBeUsed() {
        Account account = accountService
                .saveAccount("Newt Yousah", "Neoyus", "12345678", "exists");

        assertFalse(accountRepository.findAll().contains(account));
    }

    @After
    public void tearDown() {
         accountRepository.deleteAll();
    }

}
