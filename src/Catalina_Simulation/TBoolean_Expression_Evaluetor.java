package Catalina_Simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TBoolean_Expression_Evaluetor {

	private static Map<String, String> Variables = new HashMap<>();

	    public static boolean Evaluate(String Expression) {
	        Stack<Boolean> operands = new Stack<>();
	        Stack<Character> operators = new Stack<>();

//			System.out.println("Initial expression: "+expression);
	        // Replacing variables with their values
	        for (String Variable : Variables.keySet()) {
	            Expression = Expression.replaceAll(Variable, String.valueOf(Variables.get(Variable)));
	        }
//	        System.out.println("Modified expression: "+expression);
//	        System.out.println("Operators: "+operators);

	        for (char ch : Expression.toCharArray()) {
	        System.out.println((ch));
	            if (Character.isLetterOrDigit(ch)) {
	                operands.push(ch == 'T');
	            } else {
	                switch (ch) {
	                    case '(':
	                        operators.push(ch);
	                        break;
	                    case ')':
	                        while (operators.peek() != '(') {
	                            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
	                        }
	                        operators.pop(); // Rimuovi '('
	                        break;
	                    
	                   /* case '!':
	                    System.out.println("-----------");
	                     System.out.println("operands 111: "+operands);
	                    Boolean prova= operands.pop();
	                    operands.push(!prova);
	                     System.out.println("operands 222: "+operands);
	                    
	                    
	                    
	                    	System.out.println(prova);
	                    System.out.println("-----------");
	                    	//	System.out.println("2: "+operators.pop());
	                    	/*while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
	                        	System.out.println("1: "+operators.pop());
	                    		System.out.println("2: "+operators.pop());
	                            //operands.push(applyOperator(operators.pop(), operands.pop(), null));
	                    	}//
	                        
	                        break;*/
	            
	                    case '&':
	                    case '|':
	                    case '^':
	                        while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
	                            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
	                        }
	                        
	                        operators.push(ch);
	                        break;
	                }
	            }
	        }

	        while (!operators.isEmpty()) {
	            operands.push(applyOperator(operators.pop(), operands.pop(), operands.pop()));
	        }

	        return operands.pop();
	    }

	    private static  
	 boolean applyOperator(char op, Boolean b, Boolean a) {
	        switch (op) {
	           // case '!':
	             //   return !b;
	            case '&':
	                return a && b;
	            case '|':
	                return a || b;
	            case '^':
	                return a ^ b;
	            default:
	                throw new IllegalArgumentException("Invalid operator...!");
	        }
	    }

	    private static int precedence(char op) {
	        switch (op) {
	          //  case '!':
	            //    return 3; // Higher priority
	            case '&':
	            case '|':
	                return 1;
	            case '^':
	                return 2;
	            case '(':
	                return 0;
	            default:
	                return -1;
	        }
	    }

	    public static void set_Variable(String variable, Boolean value) {
	        //variables.put(variable, value);
	        if (value)
	        { Variables.put(variable, "T");}
	        else
	        { Variables.put(variable, "F");}
	    }

	   /* public static void main(String[] args) {
	    * Example
	        setVariable("g1", true);
	        setVariable("h3", true);
	        String expression = "g1 ^ (h3 | T ^ T) ^ T";
	        //String expression = "g1 & g1";
	        System.out.println("result: "+evaluate(expression));
	    }*/
	}