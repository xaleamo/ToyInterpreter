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
        PrgState prg=new PrgState(executionStack,new SymTable(), new Heap(), new Output(), new FileTable());

        String filepath="files/log";
        count++;
        //filepath+=Integer.toString(count);
        filepath+='('+key+')'+".txt";

        IRepository repo=new Repository(prg,filepath);
        Service service=new Service(repo);

        try{
            service.allStep();
        }
        catch(MyException e){
            System.out.println(e.getMessage());
        }
    }

}
