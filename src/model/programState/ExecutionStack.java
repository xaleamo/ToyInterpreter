package model.programState;

import model.programState.ADTs.MyStack;
import model.statement.Statement;
import myExceptions.MyException;

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
