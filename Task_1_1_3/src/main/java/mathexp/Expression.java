package mathexp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Abstract class representing a mathematical expression. Specific arithmetic operations and types
 * of expressions must extend this class.
 */
public sealed abstract class Expression
    permits Add, Div, Mul, Sub, Variable, Number {

    /**
     * Evaluates the expression using the provided variable values.
     *
     * @param variables a map containing variable names and their values.
     * @return the result of evaluating the expression.
     */
    protected abstract int evaluate(java.util.Map<String, Integer> variables);

    /**
     * Returns the string representation of the expression.
     *
     * @return the string representation of the expression.
     */
    public abstract String toString();

    /**
     * Prints the expression to the standard output.
     */
    public abstract void print();

    /**
     * Computes the derivative of the expression with respect to a given variable.
     *
     * @param var the variable with respect to which the derivative is taken.
     * @return the derivative of the expression.
     */
    protected abstract Expression derivative_h(String var);

    /**
     * Help-function to simplify expression.
     *
     * @return the expression.
     */
    protected abstract Expression simplify_h();


    /**
     * Simplify expression.
     *
     * @return the expression.
     */
    public Expression simplify() {
        return this.simplify_h();
    }

    /**
     * A public method to compute the derivative of the expression.
     *
     * @param var the variable with respect to which the derivative is taken.
     * @return the derivative of the expression.
     */
    public Expression derivative(String var) {
        return this.derivative_h(var);
    }

    /**
     * Evaluates the expression based on a string containing variable assignments.
     *
     * @param varAssignment a string in the format "x=5;y=10", where variables are assigned values.
     * @return the result of evaluating the expression with the provided variable values.
     */
    public int eval(String varAssignment) {
        Map<String, Integer> variables = parseVarAs(varAssignment);
        return evaluate(variables);
    }

    /**
     * Parses a string of variable assignments and returns a map of variable names and values.
     *
     * @param input a string containing variable assignments in the format "x=5;y=10".
     * @return a map of variable names and their corresponding integer values.
     * @throws IllegalArgumentException if the input string is incorrectly formatted.
     */
    private static Map<String, Integer> parseVarAs(String input) {
        Map<String, Integer> variables = new HashMap<>();

        String[] assignments = input.split(";");
        Pattern pattern = Pattern.compile("([a-zA-Z]+)\\s*=\\s*(-?\\d+)");

        for (String assignment : assignments) {
            Matcher matcher = pattern.matcher(assignment.trim());
            if (matcher.matches()) {
                String varName = matcher.group(1);
                int varValue = Integer.parseInt(matcher.group(2));
                variables.put(varName, varValue);
            } else {
                throw new IllegalArgumentException("Incorrect assignment of a variable.");
            }
        }
        return variables;
    }
}
