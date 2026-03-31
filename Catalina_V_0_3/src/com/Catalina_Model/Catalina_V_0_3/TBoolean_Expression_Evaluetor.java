package com.Catalina_Model.Catalina_V_0_3;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TBoolean_Expression_Evaluetor 
{

    private static final Map<String, String> Variables = new HashMap<>();

    /**
     * Valuta un'espressione booleana in formato stringa.
     * Supporta gli operatori &, |, ^, ! e le parentesi.
     *
     * @param expression La stringa contenente l'espressione.
     * @return Il risultato booleano dell'espressione.
     */
    public static boolean Evaluate(String expression) 
    {
        Stack<Boolean> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        // Sostituisce le variabili con i loro valori booleani ('T' o 'F')
        for (String variable : Variables.keySet()) {
            expression = expression.replaceAll(variable, Variables.get(variable));
        }

        // Rimuove eventuali spazi per semplificare il parsing
        expression = expression.replaceAll("\\s+", "");

        for (char ch : expression.toCharArray()) 
        {
            if (ch == 'T' || ch == 'F') 
            {
                operands.push(ch == 'T');
            } 
            else if (ch == '(') 
            {
                operators.push(ch);
            } 
            else if (ch == ')') 
            {
                while (!operators.isEmpty() && operators.peek() != '(')
                {
                    Apply_Top_Operator(operands, operators);
                }
                operators.pop(); // Rimuove '(' dalla pila
            } 
            else 
            { // Gestisce gli operatori !, &, |, ^
                while (!operators.isEmpty() && Precedence(ch) <= Precedence(operators.peek())) 
                {
                    Apply_Top_Operator(operands, operators);
                }
                operators.push(ch);
            }
        }

        // Applica gli operatori rimasti nella pila
        while (!operators.isEmpty()) {
            Apply_Top_Operator(operands, operators);
        }

        // Il risultato finale è l'unico elemento rimasto nella pila degli operandi
        if (operands.size() != 1) 
        {
            throw new IllegalArgumentException("In Boolean Expression Evaluetor, Wrong expression.");
        }
        return operands.pop();
    }
    
    /**
     * Applica l'operatore in cima alla pila degli operatori.
     * Distingue tra operatori unari (come '!') e binari.
     */
    private static void Apply_Top_Operator(Stack<Boolean> operands, Stack<Character> operators) 
    {
        char op = operators.pop();
        
        if (op == '!') 
        {
            if (operands.isEmpty()) throw new IllegalArgumentException("Missing operand for operator '!'");
            boolean operand = operands.pop();
            operands.push(!operand);
        } 
        else 
        {
            if (operands.size() < 2) throw new IllegalArgumentException("Missing operands for the operator " + op);
            boolean b = operands.pop();
            boolean a = operands.pop();
            
            switch (op) 
            {
                case '&':
                    operands.push(a && b);
                    break;
                case '|':
                    operands.push(a || b);
                    break;
                case '^':
                    operands.push(a ^ b);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + op);
            }
        }
    }

    /**
     * Definisce la precedenza degli operatori.
     * '!' ha la precedenza più alta.
     */
    private static int Precedence(char op) 
    {
        switch (op) {
            case '!':
                return 3; // Precedenza più alta
            case '^':
                return 2;
            case '&':
                return 1;
            case '|':
                return 1; // & e | hanno la stessa precedenza
            case '(':
                return 0;
            default:
                return -1;
        }
    }

    /**
     * Imposta il valore di una variabile booleana.
     * @param variable Il nome della variabile (es. "g1").
     * @param value Il suo valore booleano (true o false).
     */
    public static void Set_Variable(String variable, Boolean value) {
        Variables.put(variable, value ? "T" : "F");
    }
    
    /**
     * Cancella tutte le variabili memorizzate.
     */
    public static void Clear() 
    {
        Variables.clear();
    }
    
    @Override
    public String toString() {
        // Dato che 'Variables' è statico, toString() (che è un metodo
        // d'istanza) mostrerà lo stato condiviso della mappa.
        return "TBoolean_Expression_Evaluetor[CurrentVariables=" + Variables.toString() + "]";
    }

    /*
    public static void main(String[] args) {
        // Esempi di utilizzo
        System.out.println("Esempio 1: T & F -> " + Evaluate("T & F")); // Aspettato: false
        System.out.println("Esempio 2: T | F -> " + Evaluate("T | F")); // Aspettato: true
        
        // Test con operatore !
        System.out.println("Esempio 3: !T -> " + Evaluate("!T")); // Aspettato: false
        System.out.println("Esempio 4: !(T & F) -> " + Evaluate("!(T & F)")); // Aspettato: true
        System.out.println("Esempio 5: !F | T & !T -> " + Evaluate("!F | T & !T")); // true | (T & false) -> true | false -> Aspettato: true
        
        // Test con variabili
        Clear();
        set_Variable("g1", true);
        set_Variable("h3", false);
        String expression = "g1 & !h3"; // T & !F -> T & T
        System.out.println("Esempio 6: " + expression + " -> " + Evaluate(expression)); // Aspettato: true
    }
    */
}