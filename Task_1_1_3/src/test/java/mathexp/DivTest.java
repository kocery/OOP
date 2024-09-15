package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


class DivTest {

    @Test
    void testEvaluate() {
        Div divExpr = new Div(new Number(10), new Number(2));
        assertEquals(5, divExpr.evaluate(null));
    }

    @Test
    void testEvaluateWithVariables() {
        Div divExpr = new Div(new Variable("x"), new Variable("y"));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        variables.put("y", 2);

        assertEquals(5, divExpr.evaluate(variables));
    }

    @Test
    void testDivisionByZero() {
        Div divExpr = new Div(new Number(10), new Number(0));
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            divExpr.evaluate(null);
        });
        assertEquals("Division by zero.", exception.getMessage());
    }

    @Test
    void testToString() {
        Div divExpr = new Div(new Number(10), new Number(2));
        assertEquals("(10/2)", divExpr.toString());
    }

    @Test
    void testDerivative() {
        Div divExpr = new Div(new Variable("x"), new Number(2));

        // Производная x / 2 по x = 1 / 2
        assertEquals("(((1*2)-(x*0))/(x*x))", divExpr.derivative("x").toString());
    }
}
