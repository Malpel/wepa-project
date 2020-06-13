package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

        connectionService.requestConnection(sender, receiver);

        return "redirect:/";
    }

    @PutMapping("/connection/{connectionId}/accept")
    public String acceptRequest(@PathVariable Long connectionId) {
        connectionService.acceptConnection(connectionId);
        return "redirect:/";
    }

    @DeleteMapping("/connection/{connectionId}/disconnect")
    public String disconnect(@PathVariable Long connectionId) {
        connectionService.disconnect(connectionId);
        return "redirect:/";
    }
}
