package mathexp;

import java.util.Map;

/**
 * Represents the addition operation in a mathematical expression. This class extends the abstract
 * `Expression` class and defines behavior for addition.
 */
public final class Add extends Expression {

    private final Expression left;
    private final Expression right;

    /**
     * Constructs an addition expression with the given left and right sub-expressions.
     *
     * @param left  the left sub-expression
     * @param right the right sub-expression
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates the addition expression by evaluating both the left and right expressions, then
     * adding their results.
     *
     * @param variables a map containing variable names and their values.
     * @return the result of the addition.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        return left.evaluate(variables) + right.evaluate(variables);
    }

    /**
     * Returns the string representation of the addition expression.
     *
     * @return the string in the format "(left+right)".
     */
    @Override
    public String toString() {
        return "(" + left.toString() + "+" + right.toString() + ")";
    }

    /**
     * Prints the addition expression to the standard output.
     */
    @Override
    public void print() {
        System.out.print(this);
    }

    /**
     * Computes the derivative of the addition expression.
     * The derivative of a sum is the sum of the derivatives of the left and right expressions.
     *
     * @param var the variable by which the derivative is taken.
     * @return a new `Add` expression representing the derivative of the addition.
     */
    @Override
    protected Expression derivative_h(String var) {
        return new Add(left.derivative_h(var), right.derivative_h(var));
    }
}
