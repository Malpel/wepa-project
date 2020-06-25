package projekti.fileObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projekti.account.Account;
import projekti.account.AccountService;

import java.io.IOException;

@Controller
public class FileObjectController {

    @Autowired
    private FileObjectService fileObjectService;

    @Autowired
    private AccountService accountService;

    @Secured("USER")
    @Transactional
    @PostMapping("/users/{urlString}/pics")
    public String addProfilePicture(@RequestParam("file") MultipartFile file, @PathVariable String urlString) throws IOException {
        Account account = accountService.getAccountByUrlString(urlString);
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
