package view.command;

import controller.Service;
import model.program_state.*;
import model.statement.Statement;
import my_exceptions.MyException;
import repository.IRepository;
import repository.Repository;

public class RunExample extends Command {
    private static int count=0;
    Statement statement;
    public RunExample(String key, String description, Statement statement) {
        super(key,description);
        this.statement = statement;
    }
    @Override
    public void execute() {
        //construct program state, repo and service dynamically
        ExecutionStack executionStack = new ExecutionStack();
        executionStack.push(statement);
        ProgramState prg=new ProgramState(executionStack,new SymTable(),new Output(), new Heap());

        String filepath="files/log";
        count++;
        //filepath+=Integer.toString(count);
        filepath+='('+key+')'+".txt";

        IRepository repo=new Repository(prg,filepath);
        Service service=new Service(repo);

        try{
            service.executeAll();
        }
        catch(MyException e){
            System.out.println(e.getMessage());
        }
    }

}
