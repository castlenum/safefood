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
import port.safefood.controller.validator.FoodValidator;
import port.safefood.controller.validator.WithdrawValidator;
import port.safefood.domain.food.Food;
import port.safefood.domain.member.Member;
import port.safefood.domain.withdrawForm.WithdrawForm;
import port.safefood.repository.dbRepository.JpaFoodRepository;
import port.safefood.repository.dbRepository.JpaMemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class HomeController {
    private final JpaFoodRepository foodRepository;

   // @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        // 로그인한 회원이 이미 등록한 식품이 있는 경우

        //List<Food> foods = loginMember.getFoods();
        List<Food> foods = foodRepository.findByMember(loginMember.getId());
        if(!foods.isEmpty()){
            model.addAttribute("foods", foods);
        }
        return "loginHome";
    }
}


