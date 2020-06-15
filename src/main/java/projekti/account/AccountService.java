package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Account saveAccount(String name, String username, String password, String urlString) {
        return accountRepository.save(new Account(name, username, passwordEncoder.encode(password), urlString));
    }

    public Account getAccountByUrlString(String urlString) {
        return accountRepository.findByUrlString(urlString);
    }

    public Account searchByName(String name) {
        return accountRepository.findByName(name);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account findById(Long id) {
        return accountRepository.getOne(id);
    }

    @Transactional
    public void addFriend(Account one, Account two) {
        one.getConnections().add(two);
        two.getConnections().add(one);

        accountRepository.save(one);
        accountRepository.save(two);
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
