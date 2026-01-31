package model.program_state;

import model.program_state.ADTs.MyStack;
import model.statement.IStmt;

public class ExecutionStack extends MyStack<IStmt> implements Cloneable{
    @Override
    public ExecutionStack clone(){
        ExecutionStack newS=new ExecutionStack();

        for (IStmt e : stack) {
            newS.stack.add(e.clone());
        }

        return newS;
    }
}
