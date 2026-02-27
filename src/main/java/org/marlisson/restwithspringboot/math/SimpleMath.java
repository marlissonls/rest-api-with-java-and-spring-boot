package org.marlisson.restwithspringboot.math;

import org.marlisson.restwithspringboot.exception.UnsupportedMathOperationException;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;

// import java.lang.reflect.Array;
// import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleMath {

    public Double sum(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double subtract(Double numberOne, Double numberTwo) {
        return numberOne - numberTwo;
    }

    public Double multiply(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double divide(Double numberOne, Double numberTwo) {
        return numberOne + numberTwo;
    }

    public Double power(Double numberOne, Double numberTwo) {
        return Math.pow(numberOne, numberTwo);
    }

    public Double mean(double[] values) {
        return Arrays.stream(values).average().orElse(0.0);
    }

    public Double squareRoot(Double number) {
        return Math.sqrt(number);
    }

    public List<Long> factorial(double[] numericValues) {
        return Arrays.stream(numericValues)
            .mapToInt(d -> (int) d)
            .mapToLong(this::factorialOf)
            .boxed()
            .toList();
    }

    private Long factorialOf(int n) {
        if (n < 0) throw new UnsupportedMathOperationException("Number must be non-negative!");
        long result = 1;

        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
