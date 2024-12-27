import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Класс ExpressionEvaluator предоставляет методы для вычисления математических выражений.
 * Поддерживаются числа, базовые арифметические операции, скобки и переменные.
 */
public class ExpressionEvaluator {

    /**
     * Основной метод для запуска калькулятора выражений.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();

        try {
            double result = evaluateExpression(expression);
            System.out.println("� езультат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Вычисляет заданное математическое выражение.
     *
     * @param expression Математическое выражение для вычисления.
     * @return � езультат вычисления.
     * @throws Exception Если выражение некорректно.
     */
    public static double evaluateExpression(String expression) throws Exception {
        Map<String, Double> variables = new HashMap<>();
        Stack<String> variableStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        Stack<Double> valueStack = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isLetter(c)) {
                StringBuilder variable = new StringBuilder();
                while (i < expression.length() && (Character.isLetterOrDigit(expression.charAt(i)) || expression.charAt(i) == '_')) {
                    variable.append(expression.charAt(i));
                    i++;
                }
                String varName = variable.toString();
                if (!variables.containsKey(varName)) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Введите значение для " + varName + ":");
                    variables.put(varName, scanner.nextDouble());
                }
                variableStack.push(varName);
            } else if (Character.isDigit(c) || c == '.') {
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                valueStack.push(Double.parseDouble(number.toString()));
                i--;
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (operatorStack.peek() != '(') {
                    valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
                }
                operatorStack.pop();
            } else if (isOperator(c)) {
                while (!operatorStack.isEmpty() && precedence(c) <= precedence(operatorStack.peek())) {
                    valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
                }
                operatorStack.push(c);
            } else {
                throw new Exception("Недопустимый символ: " + c);
            }
            i++;
        }

        while (!operatorStack.isEmpty()) {
            valueStack.push(applyOperator(operatorStack.pop(), valueStack.pop(), valueStack.pop()));
        }

        return valueStack.pop();
    }

    /**
     * Проверяет, является ли заданный символ оператором.
     *
     * @param c Символ для проверки.
     * @return True, если символ является оператором, иначе false.
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * Возвращает приоритет заданного оператора.
     *
     * @param op Оператор.
     * @return Приоритет оператора.
     */
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    /**
     * Применяет заданный оператор к операндам.
     *
     * @param op Оператор.
     * @param b Второй операнд.
     * @param a Первый операнд.
     * @return � езультат операции.
     * @throws ArithmeticException Если происходит деление на ноль.
     * @throws IllegalArgumentException Если оператор неизвестен.
     */
    private static double applyOperator(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("Неизвестный оператор: " + op);
        }
    }
}