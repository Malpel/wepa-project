package projekti.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.account.Account;
import projekti.account.AccountService;

@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private AccountService accountService;

    @Secured("USER")
    @PostMapping("/users/{urlString}/skills")
    public String addSkill(@PathVariable String urlString, @RequestParam String skillName) {
        Account account = accountService.getAccountByUrlString(urlString);
        skillService.addSkill(skillName, account);
        return "redirect:/users/" + urlString;
    }

    @Secured("USER")
    @PostMapping("/users/{urlString}/skills/{skillId}")
    public String complimentSkill(@PathVariable String urlString, @PathVariable Long skillId) {
        Account account = accountService.getAccountByUrlString(urlString);
        skillService.addCompliment(skillId, account);
        return "redirect:/users/" + urlString;
    }
}
