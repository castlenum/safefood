package port.safefood.domain.food;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import port.safefood.domain.member.Member;
import port.safefood.repository.converter.BooleanToYNConverter;
import port.safefood.repository.converter.FoodTypeToStringConverter;
import port.safefood.repository.converter.StorageCodeToStringConverter;

import javax.persistence.*;

@Data
@Entity
@Slf4j
public class Food {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_ID")
    private Long id;

    @Column(name = "NAME")
    private String foodName;
    @Column(name = "Q_UNIT")
    private String quantityUnit;
    @Column(name = "QUANTITY")
    private Double quantity;
    @Column(name = "EXPDATE")
    private String expDate;

    @Column(name = "ALARM")  @Convert(converter = BooleanToYNConverter.class)
    private Boolean alarm; // 알람 여부

    @Column(name = "FOODTYPE")  @Convert(converter = FoodTypeToStringConverter.class)
    private FoodType foodType; // 식품 종류

    @Column(name = "S_CODE")    @Convert(converter = StorageCodeToStringConverter.class)
    private StorageCode storageCode; // 저장 방식

    @ManyToOne
    @JoinColumn(name= "MEMBER_ID")
    private Member member;

    public void setMember(Member member){
        this.member = member;
        if(!member.getFoods().contains(this)){
            member.getFoods().add(this);
        }
    }
}

