package cloudcode.v2ourbook.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class VisibilityTypeConverter implements AttributeConverter<VisibilityType, String> {

    @Override
    public String convertToDatabaseColumn(VisibilityType category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public VisibilityType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(VisibilityType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
