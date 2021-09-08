package port.safefood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import port.safefood.SessionConst;
import port.safefood.controller.validator.WithdrawValidator;
import port.safefood.domain.member.Member;
import port.safefood.domain.withdrawForm.WithdrawForm;
import port.safefood.repository.dbRepository.JpaFoodRepository;
import port.safefood.repository.dbRepository.JpaMemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class WithdrawController {
    private final WithdrawValidator withdrawValidator;
    private final JpaFoodRepository foodRepository;
    private final JpaMemberRepository memberRepository;

    @InitBinder
    public void init(WebDataBinder dataBinder) { dataBinder.addValidators(withdrawValidator); }

    @GetMapping("/withdraw")
    public String withdrawForm(HttpServletRequest request, WithdrawForm form, Model model) {
        return "/form/withdrawForm";
    }

    @PostMapping("/withdraw")
    public String withdraw(@Validated @ModelAttribute("withdrawForm") WithdrawForm form, BindingResult bindingResult,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/form/withdrawForm";
        }
        //이상이 없을 경우 회원 탈퇴 프로세스 진행.

        //음식 먼저 삭제.
        foodRepository.deleteByMember(loginMember.getId());
        //회원 탈퇴 로직 실행.
        memberRepository.deleteByeMail(loginMember.getEMail());
        HttpSession session = request.getSession(false);

        if (session != null)
            session.invalidate();

        return "redirect:/";
    }

}
