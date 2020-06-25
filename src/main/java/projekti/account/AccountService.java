package projekti.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean uniqueUsername(String username) {
        return accountRepository.findByUsername(username) == null;
    }

    public boolean uniqueUrlString(String urlString) {
        return accountRepository.findByUrlString(urlString) == null;
    }

    public Account saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account getAccountByUrlString(String urlString) {
        return accountRepository.findByUrlString(urlString);
    }

    public List<Account> searchByName(String name) {
        return accountRepository.findByNameContainingIgnoreCase(name);
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

    @Transactional
    public void removeFriend(Account one, Account two) {
        one.getConnections().remove(two);
        two.getConnections().remove(one);

        accountRepository.save(one);
        accountRepository.save(two);
    }

    public void deleteAll() {
        accountRepository.deleteAll();
    }
}
