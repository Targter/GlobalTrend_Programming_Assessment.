import java.util.Stack;

public class ValidParentheses {
    public boolean isValid(String s) {
        // Edge case: empty string is valid
        if (s == null || s.length() == 0) {
            return true;
        }

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
                stack.pop();
            } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            } else if (c == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.pop();
            } else {
                return false; // mismatched closing bracket or extra closing bracket
            }
        }

        return stack.isEmpty(); // stack should be empty for a valid sequence
    }

    public static void main(String[] args) {
        ValidParentheses validator = new ValidParentheses();

        // Test cases
        System.out.println(validator.isValid("()")); // true
        System.out.println(validator.isValid("()[]{}")); // true
        System.out.println(validator.isValid("(]")); // false
        System.out.println(validator.isValid("([)]")); // false
        System.out.println(validator.isValid("{[]}")); // true
        System.out.println(validator.isValid("")); // true
    }
}
