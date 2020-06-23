package projekti.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.account.Account;
import projekti.connection.ConnectionService;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ConnectionService connectionService;

    public List<Post> getPosts(Account account) {
        List<Account> all = account.getConnections();
        all.add(account);

        Pageable pageable = PageRequest.of(0, 25, Sort.by("timestamp").descending());

        return postRepository.findByCreatedByInAndIsCommentFalse(all, pageable);
    }

    public Post savePost(String content, Account account) {
        return postRepository.save(new Post(content, account, false));
    }

    @Transactional
    public Post likePost(Long id, Account account) {
        Post post = postRepository.getOne(id);

        if (!post.getCreatedBy().getUsername().equals(account.getUsername())) {
            post.getLiked().add(account);
            post = postRepository.save(post);
        }

        return post;
    }

    @Transactional
    public Post saveComment(String content, Account account, Long id) {
        Post comment = postRepository.save(new Post(content, account, true));
        Post post = postRepository.getOne(id);

        post.getComments().add(comment);

        return postRepository.save(post);
    }
}
