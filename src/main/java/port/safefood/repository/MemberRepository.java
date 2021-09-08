/*package port.safefood.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import port.safefood.domain.member.Member;

import java.util.*;


@Slf4j
//@Repository
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member={}", member);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) { return store.get(id); }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

    public Optional<Member> findByeMail(String eMail) {
        return findAll().stream()
                .filter(m -> m.getEMail().equals(eMail))
                .findFirst();
    }

}*/