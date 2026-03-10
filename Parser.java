package ce326.hw1;

import java.util.HashMap;

public class Parser {
    HashMap<String, Double> variables = new HashMap<>();

    public Parser(HashMap<String, Double> variables){
        this.variables = variables;
    }

    /*for REL mode */
    public void parseLine(String line) throws ParserException{
        line = line.trim();
        
        if(!line.endsWith(";") && !line.isEmpty()){
            //Error, doesn't end with semi-colon
            throw new ParserException("Expecting ; at the end of line");
        }

        //Ends correctly with ;
        proccessLine(line);
    }

    public void proccessLine(String line) throws ParserException{
        String [] commands = line.split(";");

        for (String cmd : commands){
            cmd = cmd.trim();

            if(cmd.isEmpty()){
                continue;
            }

            //Call the instruction class
            Instruction inst = new Instruction(cmd, variables);
        }
    }

    /*for File mode*/
    public void parseLine(String line, int lineNum)throws ParserException{
        line = line.trim();

        if(line.isEmpty()){
            return;
        }

        if(!line.endsWith(";") && !line.isEmpty()){
            //Error, doesn't end with semi-colon
            throw new ParserException("Expecting ; at the end of line", lineNum);
        }

        proccessLine(line, lineNum);
    }

    public void proccessLine(String line, int lineNum) throws ParserException{
        String [] commands = line.split(";");

        for (String cmd : commands){
            cmd = cmd.trim();

            if(cmd.isEmpty()){
                continue;
            }

            Instruction inst = new Instruction(cmd, variables, lineNum);
        }
    }

    

}
