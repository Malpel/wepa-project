package projekti.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        Pageable pageable = PageRequest.of(0, 25, Sort.by("timestamp").descending());

        return postRepository.findByAccountIn(all, pageable);
    }

    public Post savePost(String content, Account account) {
        return postRepository.save(new Post(content, account));
    }

    public Post likePost(Long id) {
        Post post = postRepository.getOne(id);
        // change this
        return postRepository.save(post);
    }


    public Post saveComment(String content, Account account, Long id) {
        Post comment = savePost(content, account);
        Post post = postRepository.getOne(id);

        post.getComments().add(comment);

        return postRepository.save(post);
    }
}
