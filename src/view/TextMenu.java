package view;

import view.command.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(),c);

    }

    private void printMenu(){
        for(Command c : commands.values()){
            String line=String.format("%4s:%s",c.getKey(),c.getDescription());
            System.out.println(line);
        }
    }


    public void run(){
        Scanner input = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.print("Input the option:");
            String key = input.nextLine();

            Command com=commands.get(key);
            if(com==null){
                System.out.println("\033[0;31m"+key+" is not a valid command."+"\033[0m");
                continue;
            }
            com.execute();
        }
    }
}
