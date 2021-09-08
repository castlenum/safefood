package port.safefood.repository.dbRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import port.safefood.domain.food.Food;
import port.safefood.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.TableGenerator;
import javax.transaction.Transaction;
import java.util.List;
@Data
@RequiredArgsConstructor
@Repository
@Slf4j
public class JpaFoodRepository {
    private final EntityManager entityManager;
    private final JpaMemberRepository jpaMemberRepository;


    public Food save(Food food, Long memberId) {
        Member member = jpaMemberRepository.findById(memberId);
        food.setMember(member);
        entityManager.persist(food);
        return food;
    }


    public Food findById(Long id ){return entityManager.find(Food.class, id);}


    public List<Food> findAll(){
        return entityManager.createQuery("select f from Food f", Food.class)
                .getResultList();
    }


    public List<Food> findByMember(Long memberID){
        return entityManager.createQuery("SELECT f FROM Food f WHERE f.member.id  = :memberID", Food.class)
                .setParameter("memberID", memberID).getResultList();
    }


    public void update(Long foodId, Food updateParam) {
        Food findFood = entityManager.find(Food.class, foodId);
        findFood.setFoodName(updateParam.getFoodName());
        findFood.setQuantity(updateParam.getQuantity());
        findFood.setQuantityUnit(updateParam.getQuantityUnit());
        findFood.setExpDate(updateParam.getExpDate());
        findFood.setAlarm(updateParam.getAlarm());
        findFood.setFoodType(updateParam.getFoodType());
        findFood.setStorageCode(updateParam.getStorageCode());
    }

    @Transactional
    public void deleteByMember(Long memberID){
        entityManager.createQuery("delete from Food f where f.member.id = :memberID")
                .setParameter("memberID", memberID).executeUpdate();
    }
}
