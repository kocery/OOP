package mathexp;

import java.util.Map;

/**
 * Represents a constant number in a mathematical expression. This class extends the abstract
 * `Expression` class and defines behavior for numeric constants.
 */
public final class Number extends Expression {

    private final int value;

    /**
     * Constructs a `Number` expression with the given value.
     *
     * @param value the constant numeric value.
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * Evaluates the number, which simply returns its value.
     *
     * @param variables a map containing variable names and their values.
     * @return the value of the number.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        return value;
    }

    /**
     * Returns the string representation of the number.
     *
     * @return the string representation of the number.
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Prints the number to the standard output.
     */
    @Override
    public void print() {
        System.out.print(value);
    }

    /**
     * Computes the derivative of a constant number, which is always 0.
     *
     * @param var the variable by which the derivative is taken.
     * @return a new `Number` expression representing 0.
     */
    @Override
    protected Expression derivative_h(String var) {
        return new Number(0);
    }
}
