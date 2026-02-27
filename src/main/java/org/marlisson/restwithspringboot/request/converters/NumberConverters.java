package org.marlisson.restwithspringboot.request.converters;

import org.marlisson.restwithspringboot.exception.UnsupportedMathOperationException;

import java.util.Arrays;
// import java.util.List;

public class NumberConverters {

    public static Double convertToDouble(String strNumber) throws IllegalAccessException {
        if (strNumber == null || strNumber.isEmpty())
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        String number = strNumber.replace(",", ".");
        return Double.parseDouble(number);
    }

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null || strNumber.isEmpty()) return false;
        String number = strNumber.replace(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static double[] parseCommaSeparatedDoubles(String commaNumbers) {

        return Arrays.stream(commaNumbers.split(","))
                .map(String::trim)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public static double[] parseSlashSeparatedDoubles(String slashNumbers) {
        if (slashNumbers == null || slashNumbers.isBlank()) {
            return new double[0];
        }

        return Arrays.stream(slashNumbers.split("/"))
            .map(String::trim)
            .mapToDouble(Double::parseDouble)
            .toArray();
    }
}
