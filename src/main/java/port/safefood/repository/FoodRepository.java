/*package port.safefood.repository;

import org.springframework.stereotype.Repository;
import port.safefood.domain.food.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FoodRepository{
    ///동시성 고려는 하지 않음.
    private static final Map<Long, Food> store = new HashMap<>();
    private static long sequence = 0L;

    public Food save(Food food, Long memberId) {
        food.setId(++sequence);
        food.setMemberId(memberId);
        store.put(food.getId(), food);
        return food;
    }


    public Food findById(Long id ){return store.get(id);}


    public List<Food> findAll(){return new ArrayList<>(store.values());}


    public List<Food> findByMember(Long memberID){
        return findAll().stream()
                .filter(f -> f.getMemberId().equals(memberID)).collect(Collectors.toList());
    }


    public void update(Long foodId, Food updateParam) {
        Food findFood = findById(foodId);
        findFood.setFoodName(updateParam.getFoodName());
        findFood.setQuantity(updateParam.getQuantity());
        findFood.setQuantityUnit(updateParam.getQuantityUnit());
        findFood.setExpDate(updateParam.getExpDate());
        findFood.setAlarm(updateParam.getAlarm());
        findFood.setFoodType(updateParam.getFoodType());
        findFood.setStorageCode(updateParam.getStorageCode());

    }


    public void clearStore() {
        store.clear();
    }
}
*/