package projekti.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import projekti.account.Account;
import projekti.account.AccountRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountRepository accountRepository;

    Account account;
    Account huker;
    String content = "Lorem ipsum";

    @Before
    public void init() {
        account = accountRepository.save(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));
        huker = accountRepository.save(new Account("Hugh Kerrs", "huker", "asdqwe123", "huker"));

        account.getConnections().add(huker);
        huker.getConnections().add(account);

        // change these

        accountRepository.save(account);
        accountRepository.save(huker);
    }

    @After
    public void tearDown() {
        postRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void newPostsAreSaved() {
        postService.savePost(content, account);

        List<Post> posts = postRepository.findAll();

        assertEquals(content, posts.get(0).getContent());
        assertEquals(account.getId(), posts.get(0).getAccount().getId());
    }

    @Test
    public void postsByConnectionsAreFound() {
        postRepository.save(new Post(content, 0, account));
        postRepository.save(new Post(content + " dolor sit amet", 0, huker));
        postRepository.save(new Post("Vires in numeris", 0, huker));

        List<Post> posts = postService.getPosts(account);

        assertEquals(3, posts.size());
    }
}
