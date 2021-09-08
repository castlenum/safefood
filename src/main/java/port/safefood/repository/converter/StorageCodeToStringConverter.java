package port.safefood.repository.converter;

import lombok.extern.slf4j.Slf4j;
import port.safefood.domain.food.StorageCode;

import javax.persistence.AttributeConverter;
import java.util.stream.Stream;

@Slf4j
public class StorageCodeToStringConverter implements AttributeConverter<StorageCode, String> {

    @Override
    public String convertToDatabaseColumn(StorageCode attribute) {

        //log.info("desc1? {}", attribute.getDescription());
        return attribute.getDescription();
    }

    @Override
    public StorageCode convertToEntityAttribute(String dbData) {
        StorageCode storageCode = Stream.of(StorageCode.values())
                .filter(c -> c.getDescription().equals(dbData))
                .findFirst().get();
        return storageCode;
    }
}
