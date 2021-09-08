package port.safefood.repository.converter;

import lombok.extern.slf4j.Slf4j;
import port.safefood.domain.food.FoodType;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

@Slf4j
public class FoodTypeToStringConverter implements AttributeConverter<FoodType, String> {
    @Override
    public String convertToDatabaseColumn(FoodType attribute) {

        //log.info("desc1? {}", attribute.getDescription());
        return attribute.getDescription();
    }

    @Override
    public FoodType convertToEntityAttribute(String dbData) {
       // log.info("desc2? {}", dbData);
        return Stream.of(FoodType.values())
                .filter(c -> c.getDescription().equals(dbData))
                .findFirst().get();
        //log.info("foodType? {}", foodType.getDescription());

    }
}
