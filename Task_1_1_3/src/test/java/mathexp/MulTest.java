package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


class MulTest {

    @Test
    void testEvaluate() {
        Mul mulExpr = new Mul(new Number(5), new Number(3));
        assertEquals(15, mulExpr.evaluate(null));
    }

    @Test
    void testEvaluateWithVariables() {
        Mul mulExpr = new Mul(new Variable("x"), new Variable("y"));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        variables.put("y", 3);

        assertEquals(15, mulExpr.evaluate(variables));
    }

    @Test
    void testToString() {
        Mul mulExpr = new Mul(new Number(5), new Number(3));
        assertEquals("(5*3)", mulExpr.toString());
    }

    @Test
    void testDerivative() {
        Mul mulExpr = new Mul(new Variable("x"), new Number(3));

        // Производная (x * 3) по x = 1 * 3 = 3
        assertEquals("((1*3)+(x*0))", mulExpr.derivative("x").toString());
    }
}
