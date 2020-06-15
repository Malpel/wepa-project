package projekti.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.account.Account;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPosts(Account account) {
        List<Account> all = account.getConnections();
        all.add(account);
        return postRepository.findByAccountIn(all);
    }

    public Post savePost(String content, Account account) {
        return postRepository.save(new Post(content, 0, account));
    }
}
