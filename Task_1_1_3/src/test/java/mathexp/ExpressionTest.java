package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
        assertEquals("Incorrect assignment of a variable.", exception.getMessage());
    }

    @Test
    void testEvalWithValidAndInvalidVariableAssignment() {
        Add expr = new Add(new Variable("x"), new Number(5));

        int result = expr.eval("x = 3; y = 5");
        assertEquals(8, result);
    }

    @Test
    public void testNumberSimplify() {
        Number number = new Number(5);
        assertEquals(number, number.simplify()); // Упрощение числа не должно изменять его
    }

    @Test
    public void testVariableSimplify() {
        Variable variable = new Variable("x");
        assertEquals(variable, variable.simplify()); // Переменная не упрощается
    }

    @Test
    public void testAddSimplify() {
        // (2 + 3) должно упроститься до 5
        Add addition = new Add(new Number(2), new Number(3));
        Expression simplified = addition.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(5, ((Number) simplified).getValue());

        // (x + 3) не должно упрощаться
        Add additionWithVariable = new Add(new Variable("x"), new Number(3));
        simplified = additionWithVariable.simplify();
        assertInstanceOf(Add.class, simplified);
        assertEquals(additionWithVariable.toString(), simplified.toString());
    }

    @Test
    public void testSubSimplify() {
        // (4 - 4) должно упроститься до 0
        Sub subtraction = new Sub(new Number(4), new Number(4));
        Expression simplified = subtraction.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(0, ((Number) simplified).getValue());

        // (x - 3) не должно упрощаться
        Sub subtractionWithVariable = new Sub(new Variable("x"), new Number(3));
        simplified = subtractionWithVariable.simplify();
        assertInstanceOf(Sub.class, simplified);
        assertEquals(subtractionWithVariable.toString(), simplified.toString());
    }

    @Test
    public void testMulSimplify() {
        // (2 * 0) должно упроститься до 0
        Mul multiplication = new Mul(new Number(2), new Number(0));
        Expression simplified = multiplication.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(0, ((Number) simplified).getValue());

        // (2 * 1) должно упроститься до 2
        multiplication = new Mul(new Number(2), new Number(1));
        simplified = multiplication.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(2, ((Number) simplified).getValue());

        // (x * 1) должно упроститься до x
        multiplication = new Mul(new Variable("x"), new Number(1));
        simplified = multiplication.simplify();
        assertInstanceOf(Variable.class, simplified);
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testDivSimplify() {
        // (6 / 1) должно упроститься до 6
        Div division = new Div(new Number(6), new Number(1));
        Expression simplified = division.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(6, ((Number) simplified).getValue());

        // (x / 1) должно упроститься до x
        division = new Div(new Variable("x"), new Number(1));
        simplified = division.simplify();
        assertInstanceOf(Variable.class, simplified);
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testComplexSimplify() {
        // ((2 * 0) + (4 - 4)) должно упроститься до 0
        Expression expr = new Add(
            new Mul(new Number(2), new Number(0)),
            new Sub(new Number(4), new Number(4))
        );
        Expression simplified = expr.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(0, ((Number) simplified).getValue());

        // ((3 * 1) + (2 - 2)) должно упроститься до 3
        expr = new Add(
            new Mul(new Number(3), new Number(1)),
            new Sub(new Number(2), new Number(2))
        );
        simplified = expr.simplify();
        assertInstanceOf(Number.class, simplified);
        assertEquals(3, ((Number) simplified).getValue());
    }
}
