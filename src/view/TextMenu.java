package view;

import view.command.Command;

import java.util.*;


public class TextMenu {
    private Map<String, Command> commands;
    private List<Command> sorted_commands;
    public TextMenu(){
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        commands.put(c.getKey(),c);

    }

    private void printMenu(){
        sorted_commands = new ArrayList<>(commands.values());
        sorted_commands.sort(Comparator.comparing(Command::getKey));
        System.out.println("\033[1;36m---------------------------------------------------------------------------------");
        for(Command c : sorted_commands){
            String line=String.format("%4s:%s",c.getKey(),c.getDescription());
            System.out.println(line);
        }
        System.out.println("---------------------------------------------------------------------------------\033[0m");
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
