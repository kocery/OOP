package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class NumberTest {

    @Test
    void testEvaluate() {
        Number number = new Number(5);
        assertEquals(5, number.evaluate(null));
    }

    @Test
    void testToString() {
        Number number = new Number(5);
        assertEquals("5", number.toString());
    }

    @Test
    void testDerivative() {
        Number number = new Number(5);
        assertEquals("0", number.derivative("x").toString());
    }
}