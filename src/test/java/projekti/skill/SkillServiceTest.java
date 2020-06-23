package projekti.skill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.account.Account;
import projekti.account.AccountRepository;

import static org.junit.Assert.assertEquals;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillServiceTest {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;
    private Account second;
    private Skill skill;
    private String skillName = "Test automation";

    @Before
    public void init() {
        account = accountRepository.save(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));
        skill = skillRepository.save(new Skill("4sight", 0, account));
        second = accountRepository.save(new Account("Hugh Kerrs", "huker", "asdqwe123", "huker"));
    }

    @After
    public void tearDown() {
        skillRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser("m_nem")
    public void newSkillsAreSaved() {
        skillService.addSkill(skillName, account);

        assertEquals(2, skillRepository.findAll().size());
        assertEquals(skillName, skillRepository.findAll().get(1).getName());
    }

    @Test
    @WithMockUser("huker")
    public void onlyUserCanAddSkillsForThemself() {
        skillService.addSkill(skillName, account);
        assertEquals(1, skillRepository.findAllByAccount(account).size());
    }

    @Test
    @WithMockUser("m_nem")
    public void allUsersSkillsAreFound() {
        skillService.addSkill(skillName, account);
        skillService.addSkill("test123", account);

        assertEquals(3, skillService.getSkills(account).size());
    }

    @Test
    @WithMockUser("huker")
    public void complimentsAreSaved() {
        skillService.addCompliment(skill.getId(), account);
        assertEquals(1, skillRepository.findByIdAndAccount(skill.getId(), account).getCompliments());
    }

    @Test
    @WithMockUser("m_nem")
    public void userCannotComplimentThemself() {
        skillService.addCompliment(skill.getId(), account);
        assertEquals(0, skillRepository.findByIdAndAccount(skill.getId(), account).getCompliments());
    }
}
