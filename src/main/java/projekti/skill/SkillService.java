package projekti.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.account.Account;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill addSkill(String name, Account account) {
        return skillRepository.save(new Skill(name, 0, account));
    }

    public Skill addCompliment(Long id, Account account) {
        Skill skill = skillRepository.findByIdAndAccount(id, account);
        skill.setCompliments(skill.getCompliments() + 1);
        return skillRepository.save(skill);
    }

    public List<Skill> getSkills(Account account) {
        return skillRepository.findAllByAccount(account);
    }


}
