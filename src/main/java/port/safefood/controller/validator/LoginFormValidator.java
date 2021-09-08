package port.safefood.controller.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import port.safefood.domain.loginform.LoginForm;
import port.safefood.domain.member.Member;
import port.safefood.repository.dbRepository.JpaMemberRepository;


@Component
@RequiredArgsConstructor
public class LoginFormValidator implements Validator {
    private final JpaMemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        // 아이디를 입력하지 않은 경우.
        if (!StringUtils.hasText(loginForm.getLoginId())) {
            errors.rejectValue("loginId", "required");
        }
        // 비밀번호를 입력하지 않은 경우.
        else if (!StringUtils.hasText(loginForm.getPassword())) {
            errors.rejectValue("password", "required");
        }
        // 아이디 비밀번호 둘 다 입력된 경우.
        else {
            // 아이디가 없거나 아이디가 있는데 비밀번호가 틀린 경우.
            if (memberRepository.findByeMail(loginForm.getLoginId()).isEmpty()) {
                errors.rejectValue("loginId", "wrong");
            } else {
                Member member = memberRepository.findByeMail(loginForm.getLoginId()).get();
                if (!member.getPassword().equals(loginForm.getPassword()))
                    errors.rejectValue("password", "wrong");
            }
        }
    }
}
