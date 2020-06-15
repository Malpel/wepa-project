package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projekti.connection.Connection;
import projekti.connection.ConnectionService;
import projekti.fileObject.FileObjectService;
import projekti.skill.SkillService;

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

    @GetMapping("/register")
    public String getRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@RequestParam String name,
                                  @RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String urlString) {

        accountService.saveAccount(name, username, password, urlString);
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
