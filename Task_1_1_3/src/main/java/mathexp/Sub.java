package mathexp;

import java.util.Map;

/**
 * Represents the subtraction operation in a mathematical expression. This class extends the
 * abstract `Expression` class and defines behavior for subtraction.
 */
public final class Sub extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs a subtraction expression with the given left and right sub-expressions.
     *
     * @param left  the left sub-expression.
     * @param right the right sub-expression.
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the subtraction expression by evaluating both the left and right expressions, then
     * subtracting the right value from the left value.
     *
     * @param variables a map containing variable names and their values.
     * @return the result of the subtraction.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) - right.evaluate(variables);
    }

    /**
     * Returns the string representation of the subtraction expression.
     *
     * @return the string in the format "(left-right)".
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }

    /**
     * Prints the subtraction expression to the standard output.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Computes the derivative of the subtraction expression.
     *
     * @param var the variable by which the derivative is taken.
     * @return a new `Sub` expression representing the derivative of the subtraction.
     */
    @Override
    protected Expression derivative_h(String var) {
        return new Sub(left.derivative_h(var), right.derivative_h(var));
    }


    /**
     * Simplifies Sub expression. If there are two numbers in Sub operator, it will return the
     * result of the subtraction. And if left and right expressions are equal, it will return 0.
     *
     * @return a new simplified `Sub` expression.
     */
    @Override
    public Expression simplify_h() {
        Expression simplifiedLeft = left.simplify();
        Expression simplifiedRight = right.simplify();

        if (simplifiedLeft.equals(simplifiedRight)) {
            return new Number(0);
        }

        if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
            int result =
                ((Number) simplifiedLeft).getValue() - ((Number) simplifiedRight).getValue();
            return new Number(result);
        }

        return new Sub(simplifiedLeft, simplifiedRight);
    }

}
