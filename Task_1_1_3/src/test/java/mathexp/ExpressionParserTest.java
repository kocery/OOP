package mathexp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.EmptyStackException;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class ExpressionParserTest {

    private final ExpressionParser parser = new ExpressionParser();

    @Test
    public void testParseSimpleNumber() {
        Expression expr = parser.parse("5");
        assertEquals("5", expr.toString());
        assertEquals(5, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testParseSimpleVariable() {
        Expression expr = parser.parse("x");
        assertEquals("x", expr.toString());

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 10);
        assertEquals(10, expr.evaluate(variables));
    }

    @Test
    public void testAdditionExpression() {
        Expression expr = parser.parse("2+3");
        assertEquals("(2+3)", expr.toString());
        assertEquals(5, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testSubtractionExpression() {
        Expression expr = parser.parse("7-4");
        assertEquals("(7-4)", expr.toString());
        assertEquals(3, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testMultiplicationExpression() {
        Expression expr = parser.parse("6*7");
        assertEquals("(6*7)", expr.toString());
        assertEquals(42, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testDivisionExpression() {
        Expression expr = parser.parse("20/4");
        assertEquals("(20/4)", expr.toString());
        assertEquals(5, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testCombinedExpression() {
        Expression expr = parser.parse("(2 + 3) * x");
        assertEquals("((2+3)*x)", expr.toString());

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 5);
        assertEquals(25, expr.evaluate(variables));
    }

    @Test
    public void testParenthesesHandling() {
        Expression expr = parser.parse("(3+2)*(4-1)");
        assertEquals("((3+2)*(4-1))", expr.toString());
        assertEquals(15, expr.evaluate(new HashMap<>()));
    }

    @Test
    public void testExpressionWithVariables() {
        Expression expr = parser.parse("(x + y) * z");
        assertEquals("((x+y)*z)", expr.toString());

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);
        variables.put("y", 4);
        variables.put("z", 2);

        assertEquals(14, expr.evaluate(variables));
    }

    @Test
    public void testInvalidExpression() {
        assertThrows(EmptyStackException.class, () -> parser.parse("5 + !"));
    }

    @Test
    public void testSimplifiedExpression() {
        Expression expr = parser.parse("(5 * 1) - (3 + 2)");
        Expression simplified = expr.simplify();
        assertEquals("0", simplified.toString());
    }

    @Test
    public void testSimplificationOfMultiplicationByZero() {
        Expression expr = parser.parse("x * 0");
        Expression simplified = expr.simplify();
        assertEquals("0", simplified.toString());
    }

    @Test
    public void testSimplificationOfMultiplicationByOne() {
        Expression expr = parser.parse("1 * x");
        Expression simplified = expr.simplify();
        assertEquals("x", simplified.toString());
    }

    @Test
    public void testSimplificationOfSubtractionOfSameExpressions() {
        Expression expr = parser.parse("x - x");
        Expression simplified = expr.simplify();
        assertEquals("0", simplified.toString());
    }
}
