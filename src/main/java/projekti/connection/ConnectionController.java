package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.account.Account;
import projekti.account.AccountService;

@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    AccountService accountService;

    @PostMapping("/users/{id}/connect")
    public String sendRequest(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account sender = accountService.findByUsername(auth.getName());
        Account receiver = accountService.findById(id);

        connectionService.newConnection(sender, receiver);

        return "redirect:/";
    }

    @PostMapping("/users/{id}/connect/{connectionId}")
    public String acceptRequest(@PathVariable Long connectionId) {
        connectionService.updateConnection(connectionId);
        return "redirect:/";
    }
}
