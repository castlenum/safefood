package port.safefood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import port.safefood.controller.validator.AddMemberValidator;
import port.safefood.domain.member.Member;
import port.safefood.repository.dbRepository.JpaMemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
@Slf4j
@Transactional
public class MemberController {

    private final JpaMemberRepository memberRepository;
    private final AddMemberValidator addMemberValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) { dataBinder.addValidators(addMemberValidator); }


    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "/form/addMemberForm";
    }


    @PostMapping("/add")
    public String save(@Valid @ModelAttribute Member member, BindingResult result) {
        if (result.hasErrors()) {
            return "/form/addMemberForm";
        }
        log.info("저장 전");
        memberRepository.save(member);
        log.info("저장 후");
        return "redirect:/";
    }

}