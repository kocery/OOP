package mathexp;

import java.util.Map;

/**
 * Represents the multiplication operation in a mathematical expression. This class extends the
 * abstract `Expression` class and defines behavior for multiplication.
 */
public final class Mul extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs a multiplication expression with the given left and right sub-expressions.
     *
     * @param left  the left sub-expression.
     * @param right the right sub-expression.
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the multiplication expression by evaluating both the left and right expressions,
     * then multiplying their results.
     *
     * @param variables a map containing variable names and their values.
     * @return the result of the multiplication.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) * right.evaluate(variables);
    }

    /**
     * Returns the string representation of the multiplication expression.
     *
     * @return the string in the format "(left*right)".
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }

    /**
     * Prints the multiplication expression to the standard output.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Computes the derivative of the multiplication expression using the product rule. The
     * derivative of a product is (f' * g) + (f * g'), where f and g are the left and right
     * expressions.
     *
     * @param var the variable with by which the derivative is taken.
     * @return a new `Add` expression representing the derivative of the multiplication.
     */
    @Override
    protected Expression derivative_h(String var) {
        return new Add(new Mul(left.derivative_h(var), right),
            new Mul(left, right.derivative_h(var)));
    }

    /**
     * Simplifies Mul expression. If there is one in Mul operator, it will return another part of
     * Mul expression. If there is one zero in Mul operator, it will return zero.
     *
     * @return a new simplified `Mul` expression.
     */
    @Override
    public Expression simplify_h() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if ((simplifiedLeft instanceof Number && ((Number) simplifiedLeft).getValue() == 0) ||
            (simplifiedRight instanceof Number && ((Number) simplifiedRight).getValue() == 0)) {
            return new Number(0);
        }

        if (simplifiedLeft instanceof Number && ((Number) simplifiedLeft).getValue() == 1) {
            return simplifiedRight;
        }
        if (simplifiedRight instanceof Number && ((Number) simplifiedRight).getValue() == 1) {
            return simplifiedLeft;
        }

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            int result =
                ((Number) simplifiedLeft).getValue() * ((Number) simplifiedRight).getValue();
            return new Number(result);
        }

        return new Mul(simplifiedLeft, simplifiedRight);
    }

}
