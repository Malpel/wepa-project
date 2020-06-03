package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projekti.fileObject.FileObject;
import projekti.fileObject.FileObjectService;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    FileObjectService fileObjectService;

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
        Account account = accountService.getAccountByUrl(urlString);
        model.addAttribute("account", account);
        return "profile";
    }

    @PostMapping("/users/{urlString}/pics")
    public String addProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable String urlString) throws IOException {
        Account account = accountService.getAccountByUrl(urlString);
        FileObject fo = fileObjectService.findByAccountId(account.getId());

        if (fo != null) {
            fileObjectService.deleteOne(fo);
        }

        fileObjectService.save(file, account);
        System.out.println("file saved");

        return "redirect:/users/" + urlString;
    }

    @GetMapping(value = "/users/{id}/pics", produces = "image/jpg")
    @ResponseBody
    public byte[] getProfilePic(@PathVariable Long id) {
        return fileObjectService.findByAccountId(id).getContent();
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
