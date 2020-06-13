package projekti.skill;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Before
    public void init() {
        urlString = accountRepository.save(new Account("Maydup Nem",
                "m_nem", "asdqwe123", "mnem")).getUrlString();
    }

    @After
    public void tearDown() {
        skillRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void skillsCanBeAdded() throws Exception {
        mockMvc.perform(
                post("/users/" + urlString + "/skills")
                .param("skillName", "testing"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertEquals(1, skillRepository.findAll().size());
        assertEquals("testing", skillRepository.findAll().get(0).getName());
    }

}
