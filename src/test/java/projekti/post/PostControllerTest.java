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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.account.AccountRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser("m_nem")
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
    private String content = "Carthago delenda est";

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


    @Test
    public void postsPageStatusOk() throws Exception {
        MvcResult res = mockMvc.perform(
                get("/posts"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts", "account"))
                .andReturn();

        List<Post> found = (List<Post>) res.getModelAndView().getModel().get("posts");

        assertEquals(post.getContent(), found.get(0).getContent());
    }

    @Test
    public void postingWorks() throws Exception {
        mockMvc.perform(
                post("/posts/new")
                .param("content", content))
                .andExpect(status().is3xxRedirection());

        assertEquals(content, postRepository.findAll().get(1).getContent());
    }

    @Test
    @Transactional
    public void commentingWorks() throws Exception {
        mockMvc.perform(
                post("/posts/" + post.getId() + "/comment")
                        .param("content", content))
                .andExpect(status().is3xxRedirection());

        Post comment = postRepository.findAll().get(0).getComments().get(0);

        assertEquals(content, comment.getContent());
        assertTrue(comment.isComment());

        MvcResult res = mockMvc.perform(get("/posts")).andReturn();

        List<Post> posts = (List<Post>) res.getModelAndView().getModel().get("posts");
        Post commentOnPage = posts.get(0).getComments().get(0);

        assertTrue(posts.get(0).getComments().contains(comment));
        assertEquals(content, commentOnPage.getContent());
    }

    @Test
    @WithMockUser("huker")
    @Transactional
    public void likingWorks() throws Exception {
        mockMvc.perform(
                post("/posts/" + post.getId() + "/like"))
                .andExpect(status().is3xxRedirection());

        List<Account> peopleWhoLiked = postRepository.getOne(post.getId()).getLiked();

        assertEquals(1, peopleWhoLiked.size());
        assertEquals(two, peopleWhoLiked.get(0));
    }


}
