package port.safefood.controller.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import port.safefood.SessionConst;
import port.safefood.domain.member.Member;
import port.safefood.domain.withdrawForm.WithdrawForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawValidator implements Validator {
    public final HttpServletRequest request;

    @Override
    public boolean supports(Class<?> clazz) {
        return WithdrawForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WithdrawForm form = (WithdrawForm) target;

        // 동의하지 않았을 경우.
        if (form.isChecked() == false){
            errors.rejectValue("checked", "required");
        }
        else {
            // 비밀번호를 입력하지 않은 경우.
            if (!StringUtils.hasText(form.getPassword())){
                errors.rejectValue("password", "required");
            }
            else {
                HttpSession session = request.getSession(false);
                Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
                log.info("loginMember? {}", member.getEMail());
                if (!member.getPassword().equals(form.getPassword())){
                    errors.rejectValue("password", "wrong");
                }
            }
        }
    }

}
