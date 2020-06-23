package projekti.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import projekti.account.Account;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill addSkill(String name, Account account) {
        Skill skill = null;

        if (getUser().equals(account.getUsername())) {
            skill = skillRepository.save(new Skill(name, 0, account));
        }

        return skill;
    }

    public Skill addCompliment(Long id, Account account) {
        Skill skill = skillRepository.findByIdAndAccount(id, account);

        if (!getUser().equals(account.getUsername())) {
            skill.setCompliments(skill.getCompliments() + 1);
            skillRepository.save(skill);
        }

        return skill;
    }

    public List<Skill> getSkills(Account account) {
        return skillRepository.findAllByAccount(account);
    }

    private String getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
