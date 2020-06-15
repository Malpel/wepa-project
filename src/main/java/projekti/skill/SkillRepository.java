package projekti.skill;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.account.Account;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByAccount(Account account);
    Skill findByIdAndAccount(Long id, Account account);
}
