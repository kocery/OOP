package mathexp;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a variable in a mathematical expression. This class extends the abstract `Expression`
 * class and provides behavior for evaluating and differentiating variables.
 */
public final class Variable extends Expression {

    private final String name;

    /**
     * Constructs a `Variable` expression with the given name.
     *
     * @param name the name of the variable.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Evaluates the variable by looking up its value in the provided map.
     *
     * @param variables a map containing variable names and their corresponding values.
     * @return the value of the variable.
     * @throws IllegalArgumentException if the variable is not found in the map.
     */
    @Override
    protected int evaluate(Map<String, Integer> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else {
            throw new IllegalArgumentException("Variable " + name + " not found.");
        }
    }

    /**
     * Returns the string representation of the variable (its name).
     *
     * @return the name of the variable.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Prints the variable (its name) to the standard output.
     */
    @Override
    public void print() {
        System.out.print(name);
    }

    /**
     * Computes the derivative of the subtraction expression.
     *
     * @param var the variable by which the derivative is taken.
     * @return a new `Number` expression representing either 1 (if the variable matches) or 0 (if it
     * does not).
     */
    @Override
    protected Expression derivative_h(String var) {
        return name.equals(var) ? new Number(1) : new Number(0);
    }

    /**
     * Cannot simplify the variable.
     *
     * @return this Variable.
     */
    @Override
    public Expression simplify_h() {
        return this;
    }


    /**
     * Compares this variable with another object for equality. Two `Variable` objects are
     * considered equal if they have the same name.
     *
     * @param obj the object to be compared for equality.
     * @return true if the two variables have the same name, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Variable other)) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
