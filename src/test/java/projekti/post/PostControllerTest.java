package projekti.post;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Account one;
    private Account two;
    private Post post;

    @Before
    public void init() {
        one = accountRepository.save(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));
        two = accountRepository.save(new Account("Hugh Kerrs", "huker", "asdqwe123", "huker"));
        post = postRepository.save(new Post("In vino veritas", one, false));
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
        accountRepository.deleteAll();
    }

    /*
    @Test
    @WithMockUser("m_nem")
    public void postsPageStatusOk() throws Exception {
        mockMvc.perform(
                get("/posts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andReturn();
    }
*/

}
