package projekti.skill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.account.Account;
import projekti.account.AccountService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillServiceTest {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AccountService accountService;

    private Account account;

    @Before
    public void init() {
        account = accountService.saveAccount("Maydup Nem", "m_nem", "asdqwe123", "mnem");
    }

    @Test
    public void newSkillsAreSaved() {
        String skillName = "Test automation";
        skillService.addSkill(skillName, account);
        assertEquals(1, skillRepository.findAll().size());
        assertEquals(skillName, skillRepository.findAll().get(0).getName());
    }

    @Test
    public void allUsersSkillsAreFound() {
        String skillName = "Test automation";
        skillService.addSkill(skillName, account);
        skillName = "test123";
        skillService.addSkill(skillName, account);
        assertEquals(2, skillService.getSkills(account).size());
    }

    @After
    public void tearDown() {
        accountService.deleteAll();
        skillRepository.deleteAll();
    }

}
