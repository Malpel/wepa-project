package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DefaultController {

    @Autowired
    private AccountService accountService;

    @Value("${spring.profiles.active}")
    String activeProfile;

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", activeProfile);
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String username, @RequestParam String password,
                                  @RequestParam String urlString) {

        accountService.saveAccount(firstName, lastName, username, password, urlString);

        return "redirect:/login";
    }
}
