package mathexp;

import java.util.Stack;


/**
 * A class responsible for parsing a string representation of mathematical expressions and creating
 * corresponding {@link Expression} objects.
 * Example usage:
 * <pre>{@code
 *     ExpressionParser parser = new ExpressionParser();
 *     Expression expr = parser.parse("(2 + 3) * x");
 *     expr.print();  // Output: ((2 + 3) * x)
 * }</pre>
 */
public class ExpressionParser {

    /**
     * Returns the precedence of the math operation.
     *
     * @param op some char that represents math operation.
     * @return the precedence of the current math operation.
     */
    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    /**
     * Applies some operator to the left and right part and returns the result.
     *
     * @param op    some char that represents math operation.
     * @param left  left part of the expression.
     * @param right right part of the expression.
     * @return new Add/Sub/Mul/Div expression.
     */
    private static Expression applyOperator(char op, Expression left, Expression right) {
        return switch (op) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException("Неверный оператор: " + op);
        };
    }

    /**
     * Parses a string containing a mathematical expression and returns the corresponding
     * {@link Expression} object. The following operations are supported:
     * <ul>
     *     <li>Addition (+)</li>
     *     <li>Subtraction (-)</li>
     *     <li>Multiplication (*)</li>
     *     <li>Division (/)</li>
     *     <li>Numeric constants</li>
     *     <li>Variables</li>
     * </ul>
     * <p>
     * Operations follow standard precedence.
     *
     * @param expression the string representing the mathematical expression. Example: "(2 + 3) *
     *                   x".
     * @return an {@link Expression} object representing the parsed expression.
     * @throws IllegalArgumentException if the expression contains errors or unsupported syntax.
     */
    public static Expression parse(String expression) {
        Stack<Expression> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int index = 0;

        while (index < expression.length()) {
            char currentChar = expression.charAt(index);

            if (currentChar == ' ') {
                index++;
                continue;
            }

            if (Character.isDigit(currentChar)) {
                int start = index;
                while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                    index++;
                }
                int value = Integer.parseInt(expression.substring(start, index));
                operands.push(new Number(value));
                continue;
            }

            if (Character.isLetter(currentChar)) {
                int start = index;
                while (index < expression.length() && Character.isLetter(
                    expression.charAt(index))) {
                    index++;
                }
                String varName = expression.substring(start, index);
                operands.push(new Variable(varName));
                continue;
            }

            if (currentChar == '(') {
                operators.push(currentChar);
                index++;
                continue;
            }

            if (currentChar == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    char op = operators.pop();
                    Expression right = operands.pop();
                    Expression left = operands.pop();
                    operands.push(applyOperator(op, left, right));
                }
                operators.pop();
                index++;
                continue;
            }

            if (currentChar == '+' || currentChar == '-' || currentChar == '*'
                || currentChar == '/') {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(
                    currentChar)) {
                    char op = operators.pop();
                    Expression right = operands.pop();
                    Expression left = operands.pop();
                    operands.push(applyOperator(op, left, right));
                }
                operators.push(currentChar);
            }

            index++;
        }

        while (!operators.isEmpty()) {
            char op = operators.pop();
            Expression right = operands.pop();
            Expression left = operands.pop();
            operands.push(applyOperator(op, left, right));
        }

        return operands.pop();
    }
}
