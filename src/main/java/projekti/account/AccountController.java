package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import projekti.connection.Connection;
import projekti.connection.ConnectionService;
import projekti.fileObject.FileObjectService;
import projekti.skill.SkillService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileObjectService fileObjectService;

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private SkillService skillService;

    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        } else if (!accountService.uniqueUsername(account.getUsername())
                || !accountService.uniqueUrlString(account.getUrlString())) {
            return "register";
        }

        accountService.saveAccount(account);
        return "redirect:/login";
    }

    @GetMapping("/users/{urlString}")
     public String getProfile(Model model, @PathVariable String urlString) {
        Account account = accountService.getAccountByUrlString(urlString);
        List<Connection> requests = connectionService.getRequests(account);
        model.addAttribute("account", account);
        model.addAttribute("requests", requests);
        return "profile";
    }

    // change search maybe
    @Secured("USER")
    @PostMapping("/users/search")
    public String searchUsers(Model model, @RequestParam String name) {
        Account account = accountService.searchByName(name);

        if (account == null) {
            System.out.println("Nothing found: " + name);
        }

        model.addAttribute("account", account);
        model.addAttribute("name", name);

        return "searchResults";
    }
}
