package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


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

    @Test
    void testSimplify() {
        Div div = new Div(new Number(15), new Number(3));
        Number result = new Number(15 / 3);

        assertEquals(result.getValue(), ((Number) div.simplify()).getValue());
    }

    @Test
    void testSimplifyRecursive() {
        Div div = new Div(new Mul(new Number(5), new Number(3)), new Number(3));
        Number result = new Number((5 * 3) / 3);

        assertEquals(result.getValue(), ((Number) div.simplify()).getValue());
    }
}
