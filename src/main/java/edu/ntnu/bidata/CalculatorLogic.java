package edu.ntnu.bidata;

public class CalculatorLogic {

  public CalculatorLogic() {

  }

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
