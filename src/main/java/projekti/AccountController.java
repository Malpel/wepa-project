package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    FileObjectService fileObjectService;

    @GetMapping("/users/{urlString}")
     public String getProfile(Model model, @PathVariable String urlString) {

        Account account = accountService.getProfile(urlString);
        model.addAttribute("account", account);

        return "profile";
    }

    @PostMapping("/users/{urlString}/pics")
    public String addProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable String urlString) throws IOException {

        Account account = accountService.getProfile(urlString);
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
}
