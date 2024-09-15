package mathexp;

import java.util.Map;

/**
 * Represents the division operation in a mathematical expression. This class extends the abstract
 * `Expression` class and defines behavior for division.
 */
public final class Div extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs a division expression with the given left and right sub-expressions.
     *
     * @param left  the left sub-expression (numerator).
     * @param right the right sub-expression (denominator).
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the division expression by evaluating both the left (numerator) and right
     * (denominator) expressions, then dividing their results.
     *
     * @param variables a map containing variable names and their values.
     * @return the result of the division.
     * @throws ArithmeticException if division by zero occurs.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        int rightValue = right.evaluate(variables);
        if (rightValue == 0) {
            throw new ArithmeticException("Division by zero.");
        }
        return left.evaluate(variables) / rightValue;
    }

    /**
     * Returns the string representation of the division expression.
     *
     * @return the string in the format "(left/right)".
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "/" + right.toString() + ")";
    }

    /**
     * Prints the division expression to the standard output.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Computes the derivative of the division expression using the quotient rule. The derivative of
     * a quotient is (f'g - fg') / g², where f is the numerator and g is the denominator.
     *
     * @param var the variable by which the derivative is taken.
     * @return a new `Div` expression representing the derivative of the division.
     */
    @Override
    protected Expression derivative_h(String var) {
        return new Div(new Sub(new Mul(left.derivative_h(var), right),
            new Mul(left, right.derivative_h(var))), new Mul(left, left));
    }
}
