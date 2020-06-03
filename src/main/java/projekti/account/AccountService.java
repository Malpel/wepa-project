package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account saveAccount(String name, String username, String password, String urlString) {
        return accountRepository.save(new Account(name, username, passwordEncoder.encode(password), urlString, null));
    }

    public Account getAccountByUrl(String urlString) {
        return accountRepository.findByUrlString(urlString);
    }

    public Account searchByName(String name) {
        return accountRepository.findByName(name);
    }
}
