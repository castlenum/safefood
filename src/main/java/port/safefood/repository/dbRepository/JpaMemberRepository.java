package port.safefood.repository.dbRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import port.safefood.domain.food.Food;
import port.safefood.domain.member.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Repository
@Slf4j
public class JpaMemberRepository{
    private final EntityManager entityManager;


    public Member save(Member member) {
        entityManager.persist(member);
        return member;
    }

    //로직상 반드시 존재하는 객체이므로, Optional이 아닌 Member를 반환하도록 설계.
    public Member findById(Long id) {
        return entityManager.find(Member.class, id);
    }

    public Optional<Member> findByeMail(String email) {
        //log.info("email? {}",email);
        List<Member> result = entityManager.createQuery("SELECT m FROM Member m WHERE m.eMail = :eMail", Member.class)
                .setParameter("eMail", email).getResultList();

        return result.stream().findAny();
    }

    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public void deleteByeMail(String eMail){
        entityManager.createQuery("delete from Member m where m.eMail = :eMail")
                .setParameter("eMail", eMail).executeUpdate();
    }

}
