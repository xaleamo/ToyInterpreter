package model.program_state;

import model.program_state.ADTs.MyStack;
import model.statement.Statement;

public class ExecutionStack extends MyStack<Statement> implements Cloneable{
    @Override
    public ExecutionStack clone(){
        ExecutionStack newS=new ExecutionStack();

        for (Statement e : stack) {
            newS.stack.add(e.clone());
        }

        return newS;
    }
}
