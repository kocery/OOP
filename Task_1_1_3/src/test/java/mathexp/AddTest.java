package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;


class AddTest {

    @Test
    void testEvaluate() {
        Add addExpr = new Add(new Number(5), new Number(3));
        assertEquals(8, addExpr.evaluate(null));
    }

    @Test
    void testEvaluateWithVariables() {
        Add addExpr = new Add(new Variable("x"), new Variable("y"));

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        variables.put("y", 3);

        assertEquals(8, addExpr.evaluate(variables));
    }

    @Test
    void testToString() {
        Add addExpr = new Add(new Number(5), new Number(3));
        assertEquals("(5+3)", addExpr.toString());
    }

    @Test
    void testDerivative() {
        Add addExpr = new Add(new Variable("x"), new Number(5));

        // Производная (x + 5) по x равна 1 + 0 = 1
        assertEquals("(1+0)", addExpr.derivative("x").toString());

        // Производная (x + 5) по y равна 0 + 0 = 0
        assertEquals("(0+0)", addExpr.derivative("y").toString());
    }
}
