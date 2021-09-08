/*package port.safefood;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import port.safefood.domain.food.Food;
import port.safefood.repository.FoodRepository;
import port.safefood.domain.food.FoodType;
import port.safefood.domain.member.Member;
import port.safefood.repository.MemberRepository;
import port.safefood.repository.dbRepository.JpaMemberRepository;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {


    private final JpaMemberRepository memberRepository;
    private final FoodRepository foodRepository;

    @PostConstruct
    public void init() {
        Member member  = new Member();
        member.setName(("테스터"));
        member.setEMail(("example@naver.com"));
        member.setPassword(("!@test"));
        memberRepository.save(member);
    }
}*/
