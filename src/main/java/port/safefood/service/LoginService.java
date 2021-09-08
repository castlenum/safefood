package port.safefood.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import port.safefood.domain.member.Member;
import port.safefood.repository.dbRepository.JpaMemberRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final JpaMemberRepository memberRepository;

    public Member login(String eMail, String password){
        return memberRepository.findByeMail(eMail)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
