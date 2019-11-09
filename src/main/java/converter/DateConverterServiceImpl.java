package converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.util.Date;

/**
 * @author zhaihs
 * @date 2019/11/8
 */
public class DateConverterServiceImpl implements ConversionService {
    @Autowired
    private DateConvert dateConvert;

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return sourceType == String.class && targetType == Date.class;
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType() == String.class && targetType.getType() == Date.class;
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        return (T) dateConvert.convert((String) source);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return dateConvert.convert((String) source);
    }
}
