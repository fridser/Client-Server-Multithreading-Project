package edu.ntnu.bidata;

/**
 * Calculator logic for performing arithmetic operations.
 * Supports addition, subtraction, multiplication, and division.
 */
public class CalculatorLogic {

    public CalculatorLogic() {
    }

    /**
     * Handles a command in format: "number operator number"
     * @param command The command string (e.g., "10 + 5")
     * @return Result as string or error message
     */
    public String handleCommand(String command) {
        String[] components = command.trim().split("\\s+");

        if (components.length != 3) {
            return "ERROR: Invalid command format. Expected: <number> <operator> <number>";
        }

        try {
            int num1 = Integer.parseInt(components[0]);
            int num2 = Integer.parseInt(components[2]);
            String operator = components[1];

            switch (operator) {
                case "+":
                    return String.valueOf(add(num1, num2));
                case "-":
                    return String.valueOf(subtract(num1, num2));
                case "*":
                    return String.valueOf(multiply(num1, num2));
                case "/":
                    if (num2 == 0) {
                        return "ERROR: Division by zero";
                    }
                    return String.valueOf(divide(num1, num2));
                default:
                    return "ERROR: Unknown operator. Use +, -, *, /";
            }
        } catch (NumberFormatException e) {
            return "ERROR: Invalid number format";
        }
    }

    public int add(int var1, int var2) {
        return var1 + var2;
    }

    public int subtract(int var1, int var2) {
        return var1 - var2;
    }

    public int multiply(int var1, int var2) {
        return var1 * var2;
    }

    public int divide(int var1, int var2) {
        return var1 / var2;
    }
}