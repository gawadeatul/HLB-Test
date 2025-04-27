package com.hlb;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class JavaTest {
    //E.g. Input: Automation, Output: noitamotuA
    // Please don't use method such as s.reverse()
    public static String reverseStringWithoutUsingStringMethod(String s) {
      if (s == null || s.isEmpty()) {
        return s;
      }
        return IntStream.range(0, s.length())
                .mapToObj(i -> s.charAt(s.length() - 1 - i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
    
    // Sort the integer in ASC order without using the built-in method such as ArrayUtils.sort
    public static Integer[] sortIntegers(Integer[] array) {
        if (array == null || array.length <= 1) 
		{
			return array;
		}
        Integer[] result = array.clone();
        IntStream.range(0, result.length - 1).forEach(i ->
                IntStream.range(0, result.length - i - 1).forEach(j -> {
                    if (result[j] > result[j + 1]) {
                        int temp = result[j];
                        result[j] = result[j + 1];
                        result[j + 1] = temp;
                    }
                })
        );
        return result;
    }
    
    // Check if the given date is within the date range
    public static boolean isInDateRange(Date givenDate, Date startDate, Date endDate) {
        if (givenDate == null || startDate == null || endDate == null) {
          return false;
        }
        return Optional.of(givenDate)
                .filter(d -> !d.before(startDate) && !d.after(endDate))
                .isPresent();
    }
    
    // sort the given String in ASC order without using method like Arrays.sort
    public static char[] sortStringInAscOrder(String input) {
        if (input == null || input.isEmpty()) {
          return new char[0];
        }
        char[] chars = input.toCharArray();
        IntStream.range(0, chars.length - 1).forEach(i ->
                IntStream.range(0, chars.length - i - 1).forEach(j -> {
                    if (chars[j] > chars[j + 1]) {
                        char temp = chars[j];
                        chars[j] = chars[j + 1];
                        chars[j + 1] = temp;
                    }
                })
        );
        return chars;
    }
    
    // Given a Alphanumeric, please return a character with the lowest occurrence
    // E.g. AbcdAbc123123, the answer is d as it only occurs once
    // If there is more than 1 char with the same lowest occurrence, return the first char in ASC order
    public static char lowestOccurrence(String input) {
      if (input == null || input.isEmpty()) {
        return '\0';
      }

      Map<Character, Long> countMap = input.chars()
          .mapToObj(c -> (char) c)
          .collect(Collectors.groupingBy(c -> c,Collectors.counting()));

      return countMap.entrySet().stream()
          .min((e1, e2) -> {
            int countCompare = e1.getValue().compareTo(e2.getValue());
            return countCompare != 0 ? countCompare : e1.getKey().compareTo(e2.getKey());
          })
          .map(Map.Entry::getKey)
          .orElse('\0');
    }
    
    // Given an input, please apply the provided equations (+, -, x, /)
    // E.g. input: 1.5, equations: x*2, x+10/2, x*1.5-6
    // Answer: 1st equation: x*2 = 1.5*2 = 3
    //         2nd equation: x+10/2 = 3+10/2 = 8
    //         3rd equation: x*1.5-6 = 8*1.5-6 = 6
    //         return 6.0
    public static double solveEquations(double input, String[] equations) {
      double x = input;
      if (equations == null || equations.length == 0) {
        return input; 
      }
      for (String equation : equations) {
          String replacedEquation = equation.replace("x", Double.toString(x));
          x = evaluateSimpleExpression(replacedEquation);
      }
      return x;    
    }
    
    
   public static double evaluateSimpleExpression(String expr) {
      Stack<Double> numbers = new Stack<>();
      Stack<Character> operators = new Stack<>();
      int i = 0;
      while (i < expr.length()) {
        char c = expr.charAt(i);
        if (Character.isDigit(c) || c == '.') {
          StringBuilder sb = new StringBuilder();
          while (i < expr.length() && (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
            sb.append(expr.charAt(i));
            i++;
          }
          numbers.push(Double.parseDouble(sb.toString()));
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
          while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
            double b = numbers.pop();
            double a = numbers.pop();
            numbers.push(applyOperation(operators.pop(), a, b));
          }
          operators.push(c);
          i++;
        } else {
          i++;
        }
      }
      while (!operators.isEmpty()) {
        double b = numbers.pop();
        double a = numbers.pop();
        numbers.push(applyOperation(operators.pop(), a, b));
      }
      return numbers.pop();
    }

    public static int precedence(char op) {
      if (op == '+' || op == '-')
        return 1;
      if (op == '*' || op == '/')
        return 2;
      return 0;
    }
  
    public static double applyOperation(char op, double a, double b) {
      switch (op) {
        case '+':
          return a + b;
        case '-':
          return a - b;
        case '*':
          return a * b;
        case '/':
          return a / b;
        default:
          throw new UnsupportedOperationException("Unsupported operation: " + op);
      }
    }
    
    public static void main(String[] args) {
        System.out.println("Test 1: " + reverseStringWithoutUsingStringMethod("Automation"));
        Integer[] intArray = new Integer[] { 10, 12, 54, 1, 2, -9, 8 };
        Integer[] sortedArray = sortIntegers(intArray);
        System.out.print("Test 2: ");
        for (Integer i : sortedArray) {
            System.out.print(i + ", ");
        }
        
        System.out.println();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Date startDate = sdf.parse("2024-12-01 13:09:22");
            Date endDate = sdf.parse("2025-01-09 20:10:12");
            Date givenDate = sdf.parse("2025-02-02 00:11:22");
            System.out.println("Test 3: " + isInDateRange(givenDate, startDate, endDate));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        char[] sorted = sortStringInAscOrder("testingNG311");
        System.out.print("Test 4: ");
        for (char c : sorted) {
            System.out.print(c + ", ");
        }
        System.out.println();
        System.out.println("Test 5: " + lowestOccurrence("Abc1dd23affbc1ee23u3278"));
        System.out.print("Test 6: ");
        double calculated = solveEquations(3.4, new String[] { "x*x", "x-10/2.2", "x+4-10", "x+5*8" });
        System.out.println("calculated = " + calculated);
    }
}