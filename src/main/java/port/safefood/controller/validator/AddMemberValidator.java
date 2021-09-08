package port.safefood.controller.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import port.safefood.domain.member.Member;
import port.safefood.repository.dbRepository.JpaMemberRepository;

@Component
@Slf4j
@RequiredArgsConstructor
public class AddMemberValidator implements Validator {
    private final JpaMemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;

        // 아이디를 입력하지 않은 경우.
        if (!StringUtils.hasText(member.getEMail())) {
            errors.rejectValue("eMail", "required");
        }
        // 비밀번호를 입력하지 않은 경우.
        else if (!StringUtils.hasText(member.getPassword())) {
            errors.rejectValue("password", "required");
        }else if(member.getEMail().contains("@")){
            errors.rejectValue("eMail", "wrong");
        }
        else {
            // 아이디 비밀번호 검증을 위한 전처리
            // 자바 스크립트를 따로 사용하지 않으므로, 입력 데이터를 검증기에서 전처리.
            // 특별한 공백문자는 사용하지 않았다고 가정.

            member.setEMail(member.getEMail().replaceAll(" ", "") + "@naver.com");
            log.info("member? {}", member);

            // 이미 가입된 경우.
            if(memberRepository.findByeMail(member.getEMail()).isPresent()){
                errors.rejectValue("eMail", "duplicated");
            }
        }


    }
}
