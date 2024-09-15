package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


class SubTest {

    @Test
    void testEvaluate() {
        Sub subExpr = new Sub(new Number(10), new Number(5));
        assertEquals(5, subExpr.evaluate(null));
    }

    @Test
    void testEvaluateWithVariables() {
        Sub subExpr = new Sub(new Variable("x"), new Variable("y"));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        variables.put("y", 5);

        assertEquals(5, subExpr.evaluate(variables));
    }

    @Test
    void testToString() {
        Sub subExpr = new Sub(new Number(10), new Number(5));
        assertEquals("(10-5)", subExpr.toString());
    }

    @Test
    void testDerivative() {
        Sub subExpr = new Sub(new Variable("x"), new Number(5));

        // Производная (x - 5) по x = 1 - 0 = 1
        assertEquals("(1-0)", subExpr.derivative("x").toString());
    }
}
