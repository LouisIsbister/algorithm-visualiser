package shunting_yard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShuntingYard {

    private static ArrayList<String> arr;
    
    private static Stack<String> opStack;

    private static Queue<String> log;

    record OpData(int prec, String assoc) {
    }

    private static final Map<String, OpData> ops = Map.of(
            "^", new OpData(3, "R"),
            "/", new OpData(2, "L"),
            "*", new OpData(2, "L"),
            "+", new OpData(1, "L"),
            "-", new OpData(1, "L")
        );

    public static Queue<String> algorithmLog() {
        return log;
    } 

    private static void stackAdd(String elem) {
        log.add("stack add " + elem);
    }

    private static void stackRm(String elem) {
        log.add("stack pop " + elem);
    }

    private static void outputAdd(String elem) {
        log.add("output add " + elem);
    }
    
    public static void algorithm(String eq) throws Exception {
        Pattern p = Pattern.compile("([0-9]+|\\(|\\)|\\*|\\+|\\-|\\^|\\/)");
        Matcher m = p.matcher(eq);

        arr = new ArrayList<>();
        opStack = new Stack<>();
        log = new ArrayDeque<>();
        //renderHis = new LinkedHashMap<>();

        while (m.find()) {
            String token = m.group();

            if (token.matches("[0-9]+")) {
                arr.add(token);
                outputAdd(token);
            } 
            else if (token.matches("(\\*|\\+|-|\\^|/)")) {
                while (!opStack.isEmpty() &&
                        !opStack.peek().equals("(") &&
                        (ops.get(opStack.peek()).prec() > ops.get(token).prec() ||
                                (ops.get(opStack.peek()).prec() == ops.get(token).prec() && 
                                ops.get(token).assoc().equals("L")))) {
                    arr.add(opStack.pop());
                    
                    stackRm(arr.get(arr.size() - 1));
                    outputAdd(arr.get(arr.size() - 1));
                }
                opStack.add(token);
                stackAdd(token);
            } 
            else if (token.equals("(")) {
                opStack.add(token);
                stackAdd(token);
            } 
            else if (token.equals(")")) {
                while (!opStack.isEmpty() && !opStack.peek().equals("(")) {
                    arr.add(opStack.pop());
                    stackRm(arr.get(arr.size() - 1));
                    outputAdd(arr.get(arr.size() - 1));
                    
                    //renderHis.put(arr.get(arr.size() - 1), 0);
                    if (opStack.isEmpty() && !arr.get(0).equals("(")) {
                        throw new Exception("Unmatched brackets");
                    }
                }
                if (!opStack.peek().equals("(")) {
                    throw new Exception("Unmatched brackets");
                }
                stackRm(opStack.pop() + " bin");
            } 
            else {
                throw new Exception("Unknown token: '" + token + "'");
            }
        }

        while (!opStack.isEmpty()) {
            if (opStack.peek().equals("(")) {
                System.out.println(log);
                System.out.println(arr);
                System.out.println(opStack);
                throw new Exception("Unmatched brackets");
            }
            arr.add(opStack.pop());
            stackRm(arr.get(arr.size() - 1));
            outputAdd(arr.get(arr.size() - 1));
        }

        System.out.println(log);

        //renderHis.forEach((e, c)-> {
        //    System.out.println(e + " -> " + c);
        //});

        // expression execution
        
        // Stack<Double> stack = new Stack<>();

        // while (!q.isEmpty()) {
        //     String token = q.poll();
        //     switch (token) {
        //         case "+":
        //             double operand2 = stack.pop();
        //             double operand1 = stack.pop();
        //             stack.push(operand1 + operand2);
        //             break;
        //         case "-":
        //             operand2 = stack.pop();
        //             operand1 = stack.pop();
        //             stack.push(operand1 - operand2);
        //             break;
        //         case "*":
        //             operand2 = stack.pop();
        //             operand1 = stack.pop();
        //             stack.push(operand1 * operand2);
        //             break;
        //         case "/":
        //             operand2 = stack.pop();
        //             operand1 = stack.pop();
        //             stack.push(operand1 / operand2);
        //             break;
        //         case "^":
        //             operand2 = stack.pop();
        //             operand1 = stack.pop();
        //             stack.push(Math.pow(operand1, operand2));
        //             break;
        //         default:
        //             stack.push(Double.parseDouble(token));
        //     }
        // }

        // System.out.println(stack.pop());
    }

    
}
