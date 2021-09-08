package port.safefood.controller.validator;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import port.safefood.domain.food.Food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FoodValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Food.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Food food = (Food) target;

        // 식품명이 입력되지 않은 경우.
        if (!StringUtils.hasText(food.getFoodName())) {
            // 식품명은 필수 입력값입니다.
            errors.rejectValue("foodName", "required");
        }
        // 수량이 입력되지 않았거나, 0 이하가 입력된 경우
        if (food.getQuantity() == null) {
            // 수량은 필수 입력값입니다.
            errors.rejectValue("quantity", "required");
        } else if (food.getQuantity() <= 0) {
            // 수량은 1 이상의 값을 입력해주세요.
            errors.rejectValue("quantity", "min");
        }
        // 단위가 문자가 아닌 경우.
        if (!StringUtils.hasText(food.getQuantityUnit())) {
            // 단위는 필수값입니다.
            errors.rejectValue("quantityUnit", "required");
        } else if (food.getQuantityUnit().chars().allMatch( Character::isDigit )) {
            // 단위로 숫자를 입력할 수 없습니다.
            errors.rejectValue("quantityUnit", "correct");
        }

        // 유통기한이 입력되지 않은 경우.
        if (!StringUtils.hasText(food.getExpDate())) {
            // 유통기한은 필수 입력값입니다.
            errors.rejectValue("expDate", "required");
        }else if (!checkDateFormat(food.getExpDate()) ||  food.getExpDate().length() > 10){
                // 유통기한은 YYYY-MM-DD 형식으로 입력해 주세요.
                errors.rejectValue("expDate", "correct");
        } else if (!checkExp(food.getExpDate())) {
            errors.rejectValue("expDate", "min");
        }


        // 식품 종류가 선택되지 않은 경우.
        if (food.getFoodType() == null){
            errors.rejectValue("foodType", "required");
        }
        // 저장 방식이 선택되지 않은 경우.
        if (food.getStorageCode() == null){
            errors.rejectValue("storageCode", "required");
        }

    }

    public static boolean checkDateFormat(String checkDate) {
        try{
            SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatParser.setLenient(false);
            dateFormatParser.parse(checkDate);
            return true;
        }catch (ParseException pe) {
            return false;
        }
    }

    public static boolean checkExp(String checkDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String strKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate().format(formatter);
        LocalDate kst = LocalDate.parse(strKst, formatter);
        LocalDate expDate = LocalDate.parse(checkDate, formatter);

        // 유통 기한이 kst보다 이후여야 한다.
        return 0 <= expDate.compareTo(kst);

    }
}
