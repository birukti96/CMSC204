/*
 * Class: CMSC204  CRN 21437
 * Instructor: Prof. Huseyin Aygun
 * Description:  
 * Due: 10/03/2024
 * Platform/compiler:
 * I pledge that I have completed the programming 
 * assignment independently. I have not copied the code 
 * from a student or any source. I have not given my code 
 * to any student.
   Print your Name here: Naol Gobena
*/
public class Notation {
    public static String infixToPostfix(String infix) throws InvalidNotationFormatException, QueueOverflowException, StackOverflowException, StackUnderflowException {
        MyStack<Character> stack = new MyStack<>(infix.length());
        MyQueue<Character> queue = new MyQueue<>(infix.length());

        for (char ch : infix.toCharArray()) {
            if (Character.isDigit(ch)) {
                queue.enqueue(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.top() != '(') {
                    queue.enqueue(stack.pop());
                }
                if (stack.isEmpty()) {
                    throw new InvalidNotationFormatException();
                }
                stack.pop(); // Discard the '('
            } else if (isOperator(ch)) {
                while (!stack.isEmpty() && precedence(stack.top()) >= precedence(ch)) {
                    queue.enqueue(stack.pop());
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            queue.enqueue(stack.pop());
        }

        return queue.toString();
    }

    public static String postfixToInfix(String postfix) throws InvalidNotationFormatException, StackUnderflowException, StackOverflowException {
        MyStack<String> stack = new MyStack<>(postfix.length());

        for (char ch : postfix.toCharArray()) {
            if (Character.isDigit(ch)) {
                stack.push(Character.toString(ch));
            } else if (isOperator(ch)) {
                if (stack.size() < 2) {
                    throw new InvalidNotationFormatException();
                }
                String operand2 = stack.pop();
                String operand1 = stack.pop();
                String result = "(" + operand1 + ch + operand2 + ")";
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new InvalidNotationFormatException();
        }

        return stack.pop();
    }

    public static double evaluatePostfix(String postfix) throws InvalidNotationFormatException, StackUnderflowException, StackOverflowException {
        MyStack<Double> stack = new MyStack<>(postfix.length());

        for (char ch : postfix.toCharArray()) {
            if (Character.isDigit(ch)) {
                stack.push((double) Character.getNumericValue(ch));
            } else if (isOperator(ch)) {
                if (stack.size() < 2) {
                    throw new InvalidNotationFormatException();
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                stack.push(applyOperation(ch, operand1, operand2));
            }
        }

        if (stack.size() != 1) {
            throw new InvalidNotationFormatException();
        }

        return stack.pop();
    }

    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return -1;
    }

    private static double applyOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            case '/': return operand1 / operand2;
            default: throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
