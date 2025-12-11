package model.statement;

import model.program_state.ADTs.MyIDictionary;
import model.program_state.ExecutionStack;
import model.program_state.FileTable;
import model.program_state.PrgState;
import model.type.Type;
import model.value.Id;
import my_exceptions.TypeException;

public class ForkStatement implements Statement {
    private Statement statement;
    public ForkStatement(Statement statement) {
        this.statement = statement;
    }
    @Override
    public ForkStatement clone(){
        return new ForkStatement(statement.clone());
    }

    @Override
    public MyIDictionary<Id, Type> typecheck(MyIDictionary<Id, Type> typeEnv) throws TypeException {
        statement.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState ps) {
        ExecutionStack exeStack=new ExecutionStack();
        exeStack.push(statement);
        return new PrgState(exeStack,ps.getSymTable().clone(), ps.getHeap(), ps.getOutput(), ps.getFileTable());

    }

    @Override
    public String toString(){
        return "fork("+statement.toString()+")";
    }
}
