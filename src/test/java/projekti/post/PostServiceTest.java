package projekti.post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.account.AccountRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    private Account account;
    private Account huker;
    private String content = "Lorem ipsum";
    private Post post;

    @Before
    public void init() {
        account = accountRepository.save(new Account("Maydup Nem", "m_nem", "asdqwe123", "mnem"));
        huker = accountRepository.save(new Account("Hugh Kerrs", "huker", "asdqwe123", "huker"));

        account.getConnections().add(huker);
        huker.getConnections().add(account);

        // change these

        accountRepository.save(account);
        accountRepository.save(huker);

        post = postRepository.save(new Post("Vires in numeris", account, false));
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

        assertEquals(content, posts.get(1).getContent());
        assertEquals(account.getId(), posts.get(1).getCreatedBy().getId());
    }

    @Test
    public void postsByConnectionsAreFound() {
        postRepository.save(new Post(content, account, false));
        postRepository.save(new Post(content + " dolor sit amet", huker, false));
        postRepository.save(new Post("Vires in numeris", huker, false));

        List<Post> posts = postService.getPosts(account);

        assertEquals(4, posts.size());
    }

    @Test
    @Transactional
    public void commentsAreSaved() {
        postService.saveComment("In vino veritas", huker, post.getId());

        assertEquals(1, post.getComments().size());
        assertEquals("In vino veritas", post.getComments().get(0).getContent());
        assertTrue(post.getComments().get(0).isComment());
    }

    @Test
    public void likesAreSaved() {
        Post liked = postService.likePost(post.getId(), huker);
        assertEquals(1, liked.getLiked().size());
    }

    @Test
    @Transactional
    public void userCannotLikeTheirOwnPost() {
        Post liked = postService.likePost(post.getId(), account);
        assertTrue(liked.getLiked().isEmpty());
    }
}
