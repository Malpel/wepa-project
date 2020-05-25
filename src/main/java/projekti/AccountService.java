package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account saveAccount(String firstName, String lastName, String username, String password, String urlString) {
        return accountRepository.save(new Account(firstName, lastName, username, passwordEncoder.encode(password), urlString));
    }
}
