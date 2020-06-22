package projekti.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.account.Account;
import projekti.account.AccountService;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    AccountService accountService;

    @GetMapping("/posts")
    public String getPosts(Model model) {
        Account account = getUser();
        List<Post> posts = postService.getPosts(account);

        model.addAttribute("posts", posts);
        model.addAttribute("account", account);

        return "posts";
    }

    @Secured("USER")
    @PostMapping("/posts/new")
    public String newPost(@RequestParam String content) {
        Account account = getUser();
        postService.savePost(content, account);
        return "redirect:/posts";
    }

    @Secured("USER")
    @PostMapping("/posts/{id}/comment")
    public String comment(@PathVariable Long id, @RequestParam String content) {
        Account account = getUser();
        postService.saveComment(content, account, id);
        return "redirect:/posts";
    }

    private Account getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return accountService.findByUsername(auth.getName());
    }
}
