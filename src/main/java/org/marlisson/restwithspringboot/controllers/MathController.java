package org.marlisson.restwithspringboot.controllers;

import org.marlisson.restwithspringboot.exception.UnsupportedMathOperationException;
import org.marlisson.restwithspringboot.math.SimpleMath;
import org.marlisson.restwithspringboot.request.converters.NumberConverters;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/math")
public class MathController {

    private final SimpleMath math = new SimpleMath();

    // http://localhost:8080/math/sum/3/5
    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            // @PathVariable("numberOne") String numberOne
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception {
        if(!NumberConverters.isNumeric(numberOne) || !NumberConverters.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.sum(NumberConverters.convertToDouble(numberOne), NumberConverters.convertToDouble(numberTwo));
    }

    // http://localhost:8080/subtract/sum/3/5
    @RequestMapping("/subtract/{numberOne}/{numberTwo}")
    public Double subtract(
            // @PathVariable("numberOne") String numberOne
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception {
        if(!NumberConverters.isNumeric(numberOne) || !NumberConverters.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.subtract(NumberConverters.convertToDouble(numberOne), NumberConverters.convertToDouble(numberTwo));
    }

    // http://localhost:8080/math/multiply/3/5
    @RequestMapping("/multiply/{numberOne}/{numberTwo}")
    public Double multiply(
            // @PathVariable("numberOne") String numberOne
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception {
        if(!NumberConverters.isNumeric(numberOne) || !NumberConverters.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.multiply(NumberConverters.convertToDouble(numberOne), NumberConverters.convertToDouble(numberTwo));
    }

    // http://localhost:8080/math/divide/3/5
    @RequestMapping("/divide/{numberOne}/{numberTwo}")
    public Double divide(
            // @PathVariable("numberOne") String numberOne
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception {
        if(!NumberConverters.isNumeric(numberOne) || !NumberConverters.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.divide(NumberConverters.convertToDouble(numberOne), NumberConverters.convertToDouble(numberTwo));
    }

    // http://localhost:8080/math/power/3/5
    @RequestMapping("/power/{numberOne}/{numberTwo}")
    public Double power(
            // @PathVariable("numberOne") String numberOne
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) throws Exception {
        if(!NumberConverters.isNumeric(numberOne) || !NumberConverters.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.power(NumberConverters.convertToDouble(numberOne), NumberConverters.convertToDouble(numberTwo));
    }

    // http://localhost:8080/math/squareRoot/81
    @RequestMapping("/squareRoot/{numberOne}")
    public Double squareRoot(@PathVariable String numberOne) throws Exception {
        if(!NumberConverters.isNumeric(numberOne))
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        return math.squareRoot(NumberConverters.convertToDouble(numberOne));
    }

    // http://localhost:8080/math/mean/3/5/7
    @RequestMapping("/mean/{numbers}")
    public Double mean(@PathVariable String numbers) throws Exception {
        if (numbers == null || numbers.isEmpty())
            throw new UnsupportedMathOperationException("Please provide numeric values!");

        if (!numbers.matches("^-?\\d+(\\.\\d+)?(,\\s*-?\\d+(\\.\\d+)?)*$"))
            throw new UnsupportedMathOperationException("Please set numeric values separated by commas only!");

        double[] values = NumberConverters.parseCommaSeparatedDoubles(numbers);
        return math.mean(values);
    }

    // http://localhost:8080/math/factorial/3/5/7
    @RequestMapping("/factorial/{*numbers}")
    public List<Long> factorial(@PathVariable String numbers) {

        if (numbers == null || numbers.isBlank()) {
            throw new UnsupportedMathOperationException("Please provide numeric values!");
        }

        numbers = numbers.startsWith("/") ? numbers.substring(1) : numbers;

        double[] numericValues = NumberConverters.parseSlashSeparatedDoubles(numbers);
        return math.factorial(numericValues);
    }
}
