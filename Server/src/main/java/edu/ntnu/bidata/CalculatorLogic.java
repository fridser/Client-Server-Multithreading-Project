package edu.ntnu.bidata;

/**
 * Calculator logic for performing arithmetic operations.
 * Supports addition, subtraction, multiplication, and division.
 */
public class CalculatorLogic {
        /**
         * Processes the raw input string and returns the calculation result.
         * Supported operations: A (Addition), S (Subtraction), M (Multiplication), D (Division).
         */
        public static String processRequest(String input) {
            if (input == null) return "Error: Empty input";

            try {
                // Expected format: "Number1 Number2 Operator" -> for instance "10 5 A"
                String[] parts = input.split(" ");
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);
                String operator = parts[2].toUpperCase();

                double result = 0;
                switch (operator) {
                    case "A": result = num1 + num2; break;
                    case "S": result = num1 - num2; break;
                    case "M": result = num1 * num2; break;
                    case "D":
                        if (num2 == 0) return "Error: Division by zero";
                        result = num1 / num2;
                        break;
                    default: return "Error: Unknown operator " + operator;
                }
                return String.valueOf(result);
            } catch (Exception e) {
                return "Error: Invalid format. Please use 'num1 num2 OP'";
            }
        }
    }