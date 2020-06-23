package projekti.skill;

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
import projekti.account.Account;
import projekti.account.AccountRepository;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SkillRepository skillRepository;

    private String urlString;
    private Skill skill;
    private Account account;

    @Before
    public void init() {
        account = accountRepository.save(new Account("Maydup Nem",
                "m_nem", "asdqwe123", "mnem"));

        urlString = account.getUrlString();

        skill = skillRepository.save(new Skill("Test automation", 0, account));
    }

    @After
    public void tearDown() {
        skillRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    @WithMockUser("m_nem")
    public void skillsCanBeAdded() throws Exception {
        mockMvc.perform(
                post("/users/" + urlString + "/skills")
                .param("skillName", "testing"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertEquals(2, skillRepository.findAll().size());
        assertEquals("testing", skillRepository.findAll().get(1).getName());
    }

    @Test
    public void skillCanBeComplimented() throws Exception {
        mockMvc.perform(
                post("/users/" + urlString + "/skills/" + skill.getId())
                .param("skillId", String.valueOf(skill.getId())))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        Skill liked = skillRepository.findByIdAndAccount(skill.getId(), account);

        assertEquals(1, liked.getCompliments());
    }
}
