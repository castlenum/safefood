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
import port.safefood.domain.food.Food;
import port.safefood.domain.food.FoodType;
import port.safefood.domain.food.StorageCode;
import port.safefood.domain.member.Member;
import port.safefood.repository.dbRepository.JpaFoodRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/form/foods")
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FormFoodController {
    private final JpaFoodRepository foodRepository;
    private final FoodValidator foodValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(foodValidator);
    }

    @ModelAttribute("foodTypes")
    public FoodType[] foodTypes() { return FoodType.values(); }

    @ModelAttribute("storageCodes")
    public StorageCode[] storageCodes() {return StorageCode.values();}

    @GetMapping
    public String foods(Model model, @SessionAttribute(name = "member", required = false) Member loginMember) {
        List<Food> foods = foodRepository.findByMember(loginMember.getId());
        model.addAttribute("foods", foods);
        return "foods";
    }

    @GetMapping("/{foodId}")
    public String food(@PathVariable long foodId, Model model ) {
        Food food = foodRepository.findById(foodId);
        model.addAttribute("food", food);
        return "food";
    }

    @GetMapping("/add")
    public String addForm(@ModelAttribute Food food, RedirectAttributes redirectAttributes, Model model) {

        model.addAttribute("food", new Food());
        return "/form/addForm";
    }

    @PostMapping("/add")
    public String addFood(@Validated @ModelAttribute Food food, BindingResult bindingResult, RedirectAttributes redirectAttributes, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member loginMember) {

        if (bindingResult.hasErrors()) {
            return "/form/addForm";
        }

        // 검증 결과 이상 없을 때 실행하는 로직
        Food savedFood = foodRepository.save(food, loginMember.getId());
        redirectAttributes.addAttribute("foodId", savedFood.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/";
    }

    @GetMapping("/{foodId}/edit")
    public String editForm(@PathVariable Long foodId, Model model) {

        Food food = foodRepository.findById(foodId);
        model.addAttribute("food", food);
        return "form/editForm";
    }

    @PostMapping("/{foodId}/edit")
    public String edit(@PathVariable Long foodId, @Validated @ModelAttribute Food food, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form/editForm";
        }

        foodRepository.update(foodId, food);
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
