package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


class VariableTest {

    @Test
    void testEvaluate() {
        Variable x = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(10, x.evaluate(variables));
    }

    @Test
    void testEvaluateVariableNotFound() {
        Variable x = new Variable("x");
        Map<String, Integer> variables = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            x.evaluate(variables);
        });
        assertEquals("Переменная x не найдена", exception.getMessage());
    }

    @Test
    void testToString() {
        Variable x = new Variable("x");
        assertEquals("x", x.toString());
    }

    @Test
    void testDerivative() {
        Variable x = new Variable("x");
        assertEquals("1", x.derivative("x").toString()); // d(x)/dx = 1
        assertEquals("0", x.derivative("y").toString()); // d(x)/dy = 0
    }
}
