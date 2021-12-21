package cloudcode.v2ourbook.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PostTypeConverter implements AttributeConverter<PostType, String> {

    @Override
    public String convertToDatabaseColumn(PostType category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }

    @Override
    public PostType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(PostType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}