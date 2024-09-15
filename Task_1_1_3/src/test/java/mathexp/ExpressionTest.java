package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class ExpressionTest {

    @Test
    void testEvalWithVariableAssignment() {
        Add expr = new Add(new Variable("x"), new Number(5));

        int result = expr.eval("x=3");
        assertEquals(8, result);
    }

    @Test
    void testEvalWithInvalidVariableAssignment() {
        Add expr = new Add(new Variable("x"), new Number(5));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            expr.eval("x=abc");
        });
        assertEquals("Неверное присваивание переменной", exception.getMessage());
    }

    @Test
    void testEvalWithValidAndInvalidVariableAssignment() {
        Add expr = new Add(new Variable("x"), new Number(5));

        int result = expr.eval("x = 3; y = 5");
        assertEquals(8, result);
    }
}
