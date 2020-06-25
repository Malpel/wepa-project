package projekti.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.account.Account;
import projekti.account.AccountService;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ConnectionController {

    @Autowired
    ConnectionService connectionService;

    @Autowired
    AccountService accountService;

    @Secured("USER")
    @PostMapping("/users/{id}/connect")
    public String sendRequest(@PathVariable Long id, HttpServletRequest request) {
        Account sender = accountService.findByUsername(getUser());
        Account receiver = accountService.findById(id);

        if (sender.equals(receiver)) {
            return "redirect:/";
        }

        connectionService.requestConnection(sender, receiver);

        return "redirect:/users/" + receiver.getUrlString();
    }

    @Secured("USER")
    @PostMapping("/connection/{connectionId}/accept")
    public String acceptRequest(@PathVariable Long connectionId, HttpServletRequest request) {
        connectionService.acceptConnection(connectionId);
        return "redirect:" + request.getHeader("referer");
    }

    @Secured("USER")
    @PostMapping("/connection/{connectionId}/decline")
    public String decline(@PathVariable Long connectionId, HttpServletRequest request) {
        connectionService.decline(connectionId);
        return "redirect:" + request.getHeader("referer");
    }

    @Secured("USER")
    @PostMapping("/connection/disconnect")
    public String disconnect(@RequestParam Long oneId, @RequestParam Long twoId, HttpServletRequest request) {
        Account one = accountService.findById(oneId);
        Account two = accountService.findById(twoId);

        connectionService.disconnect(one, two);

        return "redirect:" + request.getHeader("referer");
    }

    private String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
