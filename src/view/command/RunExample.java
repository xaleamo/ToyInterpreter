package view.command;

import controller.Service;
import myExceptions.MyException;

public class RunExample extends Command {
    Service service;
    public RunExample(String key,String description,Service service) {
        super(key,description);
        this.service = service;
    }
    @Override
    public void execute() {
        try{
            service.executeAll();
        }
        catch(MyException e){
            System.out.println(e.getMessage());
        }
    }

}
