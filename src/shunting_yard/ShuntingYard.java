package shunting_yard;

import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuntingYard {

    record OpData(int prec, char assoc) {
    }

    private static final Map<String, OpData> ops = Map.of(
            "^", new OpData(3, 'R'),
            "/", new OpData(2, 'L'),
            "*", new OpData(2, 'L'),
            "+", new OpData(1, 'L'),
            "-", new OpData(1, 'L'));

    public static void algorithm(String eq) throws Exception {
        Pattern p = Pattern.compile("([0-9]+|\\(|\\)|\\*|\\+|\\-|\\^|\\/)");
        Matcher m = p.matcher(eq);

        Queue<String> q = new ArrayDeque<>();
        Stack<String> opStack = new Stack<>();

        while (m.find()) {
            String token = m.group();

            if (token.matches("[0-9]+")) {
                q.add(token);
            } else if (token.matches("(\\*|\\+|-|\\^|/)")) {
                while (!opStack.isEmpty() &&
                        !opStack.peek().equals("(") &&
                        (ops.get(opStack.peek()).prec() > ops.get(token).prec() ||
                                (ops.get(opStack.peek()).prec() == ops.get(token).prec()
                                        && ops.get(token).assoc() == 'L'))) {
                    q.add(opStack.pop());
                }
                opStack.add(token);
            } else if (token.equals("(")) {
                opStack.add(token);
            } else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    q.add(opStack.pop());
                    if (opStack.isEmpty() && !q.peek().equals("(")) {
                        throw new Exception("Unmatched brackets");
                    }
                }
                if (!opStack.peek().equals("(")) {
                    throw new Exception("Unmatched brackets");
                }

                opStack.pop();
            }
        }

        while (!opStack.isEmpty()) {
            if (opStack.peek().equals("(")) {
                throw new Exception("Unmatched brackets");
            }
            q.add(opStack.pop());
        }

        Stack<Double> stack = new Stack<>();

        while (!q.isEmpty()) {
            String token = q.poll();
            switch (token) {
                case "+":
                    double operand2 = stack.pop();
                    double operand1 = stack.pop();
                    stack.push(operand1 + operand2);
                    break;
                case "-":
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    stack.push(operand1 - operand2);
                    break;
                case "*":
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    stack.push(operand1 * operand2);
                    break;
                case "/":
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    stack.push(operand1 / operand2);
                    break;
                case "^":
                    operand2 = stack.pop();
                    operand1 = stack.pop();
                    stack.push(Math.pow(operand1, operand2));
                    break;
                default:
                    stack.push(Double.parseDouble(token));
            }
        }

        System.out.println(stack.pop());
    }
}
