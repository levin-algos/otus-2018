package ru.otus.algo.converters;

import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import ru.otus.algo.Square;

import java.util.Collections;
import java.util.HashSet;

public class SquareConverter implements ArgumentConverter {

    @Override
    public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        checkObject(o);
        String square = (String) o;
        return Square.valueOf(square);
    }

    private void checkObject(Object o) {
        if (o == null) {
            throw new ArgumentConversionException("Cannot convert null source object");
        }

        if (!o.getClass().equals(String.class)) {
            throw new ArgumentConversionException(
                    "Cannot convert source object because it's not a string"
            );
        }

        String sourceString = (String) o;
        if (sourceString.trim().isEmpty()) {
            throw new ArgumentConversionException(
                    "Cannot convert an empty source string"
            );
        }
    }
}
