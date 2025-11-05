package View;

import Controller.Service;
import MyExceptions.FileException;
import MyExceptions.RepositoryException;

import java.util.Scanner;


public class UI {
    Service service;
    public UI(Service service){this.service = service;}

    public void run(){
        Scanner input = new Scanner(System.in);
        int option=0;
        while(true){
            printMenu();
            try {
                option =Integer.parseInt(input.nextLine());
            }
            catch(NumberFormatException e){
                System.out.println("Input must be an integer.");
                continue;
            }
            if(option<0) return;
            runProgram(option);
        }
    }

    private void printMenu(){
        System.out.println("-------------------");
        System.out.println("-1. Exit.");
        System.out.println("0. Load program 0.");
        System.out.println("1. Load program 1.");
        System.out.println("2. Load program 2.");
        System.out.println("3. Load program 3.");
        System.out.println("      ~~~~~~~");
        System.out.println("4. Load faulty program 1.");
        System.out.println("5. Load faulty program 2.");
        System.out.println("6. Load faulty program 3.");
        System.out.println("7. Load faulty program 4.");
        System.out.println("8. Load faulty program 5.");
        System.out.println("-------------------");
    }
    private void runProgram(int p){
        try {
            service.setProgramState(p);
            service.setDisplayFlag(true);
            service.executeAll();
        }
        catch (RepositoryException e){
            System.out.println(e.getMessage());
        }
    }
}
