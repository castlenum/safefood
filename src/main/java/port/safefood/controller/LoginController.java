package port.safefood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import port.safefood.SessionConst;
import port.safefood.controller.validator.LoginFormValidator;
import port.safefood.domain.loginform.LoginForm;
import port.safefood.domain.member.Member;
import port.safefood.service.LoginService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;



@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LoginFormValidator loginFormValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) { dataBinder.addValidators(loginFormValidator); }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "/form/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,  HttpServletRequest request) {
        
        //loginForm에 문제가 있으면
        if (bindingResult.hasErrors()) {
            return "/form/loginForm";
        }


        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        //member가 DB에 있을 경우.
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null)
            session.invalidate();

        return "redirect:/";
    }

}
