package projekti;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @Value("${spring.profiles.active}")
    String activeProfile;

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", activeProfile);
        return "index";
    }
}
