package edu.ntnu.bidata;

/**
 * Represents a calculator that can add, subtract, multiply and divide.
 * Can take an operation as a String with the format "var1 operation var2" and
 * perform the operation.
 */
public class CalculatorLogic {

  /**
   * Creates an instance of the calculator logic.
   */
  public CalculatorLogic() {

  }

  /**
   * Parses the given command, executes it and gives an integer result.
   *
   * @param command The command to be executed. Format: "var1 operation var2".
   * @return The result of the given operation.
   */
  public int handleCommand(String command) {
    String[] components = command.split(" ");
    if (components[1].equals("+")) {
      return add(Integer.getInteger(components[0]), Integer.getInteger(components[2]));
    }
    if (components[1].equals("-")) {
      return subtract(Integer.getInteger(components[0]), Integer.getInteger(components[2]));
    }
    if (components[1].equals("*")) {
      return multiply(Integer.getInteger(components[0]), Integer.getInteger(components[2]));
    }
    if (components[1].equals("/")) {
      return divide(Integer.getInteger(components[0]), Integer.getInteger(components[2]));
    }
    else {
      return 0;
    }
  }

  /**
   * Adds the given variables together.
   *
   * @param var1 One of the numbers to be added.
   * @param var2 One of the numbers to be added.
   * @return The sum of the given variables.
   */
  public int add(int var1, int var2) {
    return var1 + var2;
  }

  /**
   * Subtracts one number from the other.
   *
   * @param var1 The variable to be subtracted from.
   * @param var2 The number that is being subtracted from var1.
   * @return The result of var1 - var2.
   */
  public int subtract(int var1, int var2) {
    return var1 - var2;
  }

  /**
   * Multiplies the given variables.
   *
   * @param var1 One of the variables to be multiplied.
   * @param var2 One of the variables to be multiplied.
   * @return The product of var1*var2.
   */
  public int multiply(int var1, int var2) {
    return var1 * var2;
  }

  /**
   * Divides the first given variable by the second given variable.
   *
   * @param var1 The variable to be divided.
   * @param var2 The variable var1 is being divided by.
   * @return The result of var1/var2.
   */
  public int divide(int var1, int var2) {
    return var1 / var2;
  }
}
