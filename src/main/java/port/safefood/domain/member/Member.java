package port.safefood.domain.member;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import port.safefood.domain.food.Food;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "E_MAIL")
    private String eMail;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Food> foods = new ArrayList<>();

}
