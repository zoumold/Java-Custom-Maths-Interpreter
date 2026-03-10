package ce326.hw1;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Interpreter{
    public static void main(String[] args){

        //Make hashmap for variables
        HashMap<String, Double> variables = new HashMap<>();

        Parser parser = new Parser(variables);

        if(args.length == 0){
            //REL mode
            Scanner sc = new Scanner(System.in);

            //main loop
            while(true){
                System.out.print("> ");

                if(!sc.hasNextLine()){
                    break;
                }

                String line = sc.nextLine();

                try{
                    parser.parseLine(line);
                }
                catch(ParserException e){
                    System.out.println(e.getMessage());
                }
            }
            sc.close();
        }
        else if (args.length == 1){
            String filename = args[0];

            try{
                File file = new File(filename);
                Scanner fileScanner = new Scanner(file);

                int lineNum = 1;

                //main loop
                while(fileScanner.hasNextLine()){
                    String line = fileScanner.nextLine();

                    try{
                        parser.parseLine(line, lineNum);
                    }
                    catch(ParserException e){
                        System.out.println(e.getMessage());
                    }
                    lineNum++;
                }
                fileScanner.close();
            }
            catch (FileNotFoundException e){
                System.out.println("Error: File Not Found.");
            }
        }
        else{
            System.out.println("Error. Too many arguments. Only use 0 or 1.");
        }
    }
}