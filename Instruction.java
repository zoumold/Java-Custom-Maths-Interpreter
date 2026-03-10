package ce326.hw1;

import java.util.LinkedList;
import java.util.Stack;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Instruction{
    private HashMap<String, Double> variables;
    private int lineNum;

    public Instruction(String command, HashMap<String, Double> variables) throws ParserException{
        this.variables = variables;
        LinkedList<String> tokens, rpnQueue;
        
        //Tokens
        tokens = makeTokens(command);
        //If first token print, remove
        if(tokens.get(0).equals("print")){tokens.remove(0);}
        //Check for errors 
        checkErrors(tokens);
        //RPN
        rpnQueue = RPN(tokens);
        //Evaluate
        Evaluate(rpnQueue);
    }

    public Instruction(String command, HashMap<String, Double> variables, int lineNum) throws ParserException{
        this.variables = variables;
        this.lineNum = lineNum;

        LinkedList<String> tokens, rpnQueue;
        
        //Tokens
        tokens = makeTokens(command);
        //If first token print, remove
        if(tokens.get(0).equals("print")){tokens.remove(0);}
        //Check for errors 
        checkErrors(tokens, lineNum);
        //RPN
        rpnQueue = RPN(tokens);
        //Evaluate
        Evaluate(rpnQueue, lineNum);
    }

    private LinkedList<String> makeTokens(String command){
        LinkedList<String> queue = new LinkedList<>();

        String regex = "(?<![0-9a-zA-Z)])-?[0-9]+(\\.[0-9]+)?|[a-zA-Z]+|[+\\-*/^=()]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);

        while (matcher.find()){
            String token = matcher.group();
            queue.add(token);
        }

        return queue;
    }

    private void checkErrors(LinkedList<String> tokens) throws ParserException {
        int parenthCount=0, equalsCount=0;
        String prevToken = null, tkn;

        for(int i=0; i<tokens.size(); i++){
            tkn=tokens.get(i);
            if(i>0){prevToken = tokens.get(i-1);}

            if(tkn.equals("(")){
                parenthCount++;
            }
            else if (tkn.equals(")")){
                parenthCount--;
                if(parenthCount < 0){
                    throw new ParserException("Expecting ( before closing");
                }
            }

            if(tkn.equals("=")){
                equalsCount++;
                if(equalsCount > 1){
                    throw new ParserException("Multiple assignment operator in expression");
                }
            }

            if(checkType(tkn) == 1){
                //Opperand as first or after parenthesis
                if (prevToken == null || prevToken.equals("(")) {
                    throw new ParserException("Expecting operand between operators");
                }
            }

            if(prevToken != null){
                if((checkType(tkn) == 1) && (checkType(prevToken) == 1)){
                    throw new ParserException("Expecting operand between operators");
                }

                if((checkType(tkn) == 2 || checkType(tkn) == 3) && (checkType(prevToken) == 2 || checkType(prevToken) == 3)){
                    throw new ParserException("Expecting operator between operands");
                }
            }

        }
        if(!tokens.isEmpty()){
            String lastToken = tokens.get(tokens.size() - 1);
            if(checkType(lastToken) == 1){
                throw new ParserException("Expecting operand between operators");
            }
        }
        if(parenthCount > 0){
            throw new ParserException("Expecting ) at end of expression");
        }
    }

    private void checkErrors(LinkedList<String> tokens, int lineNum) throws ParserException {
        int parenthCount=0, equalsCount=0;
        String prevToken = null, tkn;

        for(int i=0; i<tokens.size(); i++){
            tkn=tokens.get(i);
            if(i>0){prevToken = tokens.get(i-1);}

            if(tkn.equals("(")){
                parenthCount++;
            }
            else if (tkn.equals(")")){
                parenthCount--;
                if(parenthCount < 0){
                    throw new ParserException("Expecting ( before closing", lineNum);
                }
            }

            if(tkn.equals("=")){
                equalsCount++;
                if(equalsCount > 1){
                    throw new ParserException("Multiple assignment operator in expression", lineNum);
                }
            }

            if(checkType(tkn) == 1){
                //Opperand as first or after parenthesis
                if (prevToken == null || prevToken.equals("(")) {
                    throw new ParserException("Expecting operand between operators", lineNum);
                }
            }

            if(prevToken != null){
                if((checkType(tkn) == 1) && (checkType(prevToken) == 1)){
                    throw new ParserException("Expecting operand between operators", lineNum);
                }

                if((checkType(tkn) == 2 || checkType(tkn) == 3) && (checkType(prevToken) == 2 || checkType(prevToken) == 3)){
                    throw new ParserException("Expecting operator between operands", lineNum);
                }
            }
        }
        if(!tokens.isEmpty()){
            String lastToken = tokens.get(tokens.size() - 1);
            if(checkType(lastToken) == 1){
                throw new ParserException("Expecting operand between operators", lineNum);
            }
        }
        if(parenthCount > 0){
            throw new ParserException("Expecting ) at the end of expression",lineNum);
        }
    }

    /*Returs 1 if token is opperand
    Return 2 if token is varaible 
    Return 3 if tokes in number
    Returns 0 if nothing of the two */
    private int checkType(String token){
        //Check tkn not null
        if(token==null){
            return 0;
        }
        String regex1 = "[+\\-*/^=]";
        String regex2 = "[a-zA-Z]+";
        String regex3 = "-?[0-9]+(\\.[0-9]+)?";

        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);
        Pattern pattern3 = Pattern.compile(regex3);

        Matcher opp = pattern1.matcher(token);
        Matcher variable = pattern2.matcher(token);
        Matcher number = pattern3.matcher(token);

        if(opp.matches()){return 1;}
        else if(variable.matches()){return 2;}
        else if(number.matches()){return 3;}
        else{return 0;}
    }

    private LinkedList<String> RPN(LinkedList<String> tokens){
        LinkedList<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        String popStack=null;

        for(String tkn : tokens){
            if(checkType(tkn) == 2 || checkType(tkn) == 3){
                /*Number or Variable */
                queue.add(tkn);
            }
            else if(checkType(tkn) == 1){
                /*Opperator */
                while(!stack.isEmpty() && !stack.peek().equals("(") && getPriority(stack.peek()) >= getPriority(tkn)){
                    popStack = stack.pop();
                    queue.add(popStack);
                }
                stack.push(tkn);
            }
            else if(tkn.equals("(")){
                stack.push(tkn);
            }
            else if(tkn.equals(")")){
                popStack = stack.pop();
                while(!popStack.equals("(")){
                    queue.add(popStack);
                    popStack = stack.pop();
                }
            }
        }
        while(!stack.isEmpty()){
            popStack = stack.pop();
            queue.add(popStack);
        }

        return queue;
    }

    private void Evaluate(LinkedList<String> queue) throws ParserException{
        String assignmentVariable = null, tkn;
        int res;
        double variableVal = 0.0, pop1=0, pop2=0, result=0;
        double finalAnswer=0;

        Stack<Double> stack = new Stack<>();

        for (int i=0; i<queue.size(); i++){
            tkn = queue.get(i);
            /*Check if it is variable*/
            res = checkType(tkn);

            if(res == 2){
                //Check if it's first token ---> assignment (or print if no =), or not ----> already assigned
                if(i==0 && queue.contains("=")){
                    //Assignmnent
                    assignmentVariable = tkn;
                }
                else{
                    //Check if variable truly exists
                    if(!variables.containsKey(tkn)){
                        throw new ParserException("Unknown variable " + tkn);
                    }
                    else{
                        variableVal = variables.get(tkn);
                        stack.push(variableVal);
                    }
                }
            }
            else if (res == 3){
                stack.push(Double.parseDouble(tkn));
            }
            else if(res == 1 && !tkn.equals("=")){
                pop1 = stack.pop();
                pop2 = stack.pop();

                switch(tkn){
                    case("+"):{
                        result = pop2 + pop1;
                        break;
                    }
                    case("-"):{
                        result = pop2 - pop1;
                        break;
                    }
                    case("*"):{
                        result = pop2 * pop1;
                        break;
                    }
                    case("/"):{
                        result = pop2 / pop1;
                        break;
                    }
                    case("^"):{
                        result = Math.pow(pop2,pop1);
                        break;
                    }
                }
                stack.push(result);
            }
        }
        finalAnswer = stack.pop();

        if(assignmentVariable != null){
                //Assign it
                variables.put(assignmentVariable, finalAnswer);
            }
        else{
            //Print statement
            //Check if int or double 
            if(finalAnswer % 1 == 0){
                //Int
                System.out.format("  %.0f\n", finalAnswer);
            }
            else{
                System.out.format("  %.6f\n", finalAnswer);
            }
        }
    }

    private void Evaluate(LinkedList<String> queue, int lineNum) throws ParserException{
        String assignmentVariable = null, tkn;
        int res;
        double variableVal = 0.0, pop1=0, pop2=0, result=0;
        double finalAnswer=0;

        Stack<Double> stack = new Stack<>();

        for (int i=0; i<queue.size(); i++){
            tkn = queue.get(i);
            /*Check if it is variable*/
            res = checkType(tkn);

            if(res == 2){
                //Check if it's first token ---> assignment (or print if no =), or not ----> already assigned
                if(i==0 && queue.contains("=")){
                    //Assignmnent
                    assignmentVariable = tkn;
                }
                else{
                    //Check if variable truly exists
                    if(!variables.containsKey(tkn)){
                        throw new ParserException("Unknown variable " + tkn, lineNum);
                    }
                    else{
                        variableVal = variables.get(tkn);
                        stack.push(variableVal);
                    }
                }
            }
            else if (res == 3){
                stack.push(Double.parseDouble(tkn));
            }
            else if(res == 1 && !tkn.equals("=")){
                pop1 = stack.pop();
                pop2 = stack.pop();

                switch(tkn){
                    case("+"):{
                        result = pop2 + pop1;
                        break;
                    }
                    case("-"):{
                        result = pop2 - pop1;
                        break;
                    }
                    case("*"):{
                        result = pop2 * pop1;
                        break;
                    }
                    case("/"):{
                        result = pop2 / pop1;
                        break;
                    }
                    case("^"):{
                        result = Math.pow(pop2,pop1);
                        break;
                    }
                }
                stack.push(result);
            }
        }
        finalAnswer = stack.pop();

        if(assignmentVariable != null){
                //Assign it
                variables.put(assignmentVariable, finalAnswer);
            }
        else{
            //Print statement
            //Check if int or double 
            if(finalAnswer % 1 == 0){
                //Int
                System.out.format("  %.0f\n", finalAnswer);
            }
            else{
                System.out.format("  %.6f\n", finalAnswer);
            }
        }
    }

    private int getPriority(String check){
        if (check.equals("^")) return 3;
        if (check.equals("*") || check.equals("/")) return 2;
        if (check.equals("+") || check.equals("-")) return 1;
        if (check.equals("=")) return 0;
        return -1;
    }
}